package co.dev.victorroe.api;

import co.dev.victorroe.api.dto.product.AddStockDTO;
import co.dev.victorroe.api.dto.product.RemoveStockDTO;
import co.dev.victorroe.api.dto.product.RequestProductDTO;
import co.dev.victorroe.api.dto.product.UpdateProductDTO;
import co.dev.victorroe.api.mapper.ProductDTOMapper;
import co.dev.victorroe.usecase.product.*;
import co.dev.victorroe.usecase.exception.InsufficientStockException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class ProductHandler {

    private final CreateProductUseCase repositoryCreate;
    private final FindProductByIdUseCase repositoryFindById;
    private final FindAllProductUseCase repositoryFinAllProducts;
    private final SearchUniqueProductUseCase repositorySearchUniqueProduct;
    private final SearchPaginatedProductsUseCase repositorySearchPaginatedProducts;
    private final UpdateProductUseCase repositoryUpdateProduct;
    private final DeleteProductUseCase repositoryDeleteProduct;
    private final AddStockUseCase repositoryAddStock;
    private final RemoveStockUseCase repositoryRemoveStock;
    private final ProductDTOMapper mapper;

    public Mono<ServerResponse> createProduct(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(RequestProductDTO.class)
                .doOnNext(dto -> log.info("[createProduct] Creando producto: {}", dto))
                .map(mapper::toRequest)
                .flatMap(repositoryCreate::create)
                .doOnSuccess(saved -> log.info("[createProduct] Producto creado exitosamente: {}", saved))
                .map(mapper::toResponse)
                .flatMap(dto -> ServerResponse.status(HttpStatus.CREATED)
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(Map.of("message", "Product created successfully"))
                )
                .onErrorResume(IllegalArgumentException.class, error -> {
                            log.warn("[createProduct] Error de validacion: {}", error.getMessage());
                            return ServerResponse.badRequest()
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .bodyValue(Map.of("message", error.getMessage()));
                        }
                );

    }

    public Mono<ServerResponse> findProductById(ServerRequest serverRequest) {
        Long id = Long.parseLong(serverRequest.pathVariable("id"));
        return repositoryFindById.findById(id)
                .doOnNext(product -> log.info("[findProductById]Buscando producto con ID: {}", product.getId()))
                .doOnSuccess(product -> log.info("[findProductById] Se ha encontrado el producto con ID: {}", product.getId()))
                .map(mapper::toResponse)
                .flatMap(dto -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(dto))
                .switchIfEmpty(ServerResponse.notFound().build())
                .onErrorResume(error -> ServerResponse.badRequest()
                        .contentType(MediaType.APPLICATION_JSON).build())
                .doOnError(err -> log.error("[findProductById] Producto no encontrado: {} ", err.getMessage()));
    }

    public Mono<ServerResponse> findAllProducts(ServerRequest serverRequest) {

        int page = serverRequest.queryParam("page")
                .map(Integer::parseInt)
                .orElse(0);
        final int pageSize = 10;
        log.info("[finAllProducts] Buscando productos en la pagina: {}", page);
        return repositoryFinAllProducts.apply(page, pageSize)
                .flatMap(pageResult -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(pageResult));
    }

    public Mono<ServerResponse> searchProducts(ServerRequest serverRequest) {
        final var idOpt = serverRequest.queryParam("id").map(Long::parseLong);
        final var skuOpt = serverRequest.queryParam("sku");
        final var nameOpt = serverRequest.queryParam("name");

        if (idOpt.isPresent()) {
            log.info("[searchProducts] Buscando por ID: {}", idOpt.get());
            return repositorySearchUniqueProduct.byId(idOpt.get())
                    .map(mapper::toResponse)
                    .flatMap(dto -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).bodyValue(dto))
                    .switchIfEmpty(ServerResponse.notFound().build());
        }

        if (skuOpt.isPresent()) {
            log.info("[searchProducts] Buscando por SKU: {}", skuOpt.get());
            return repositorySearchUniqueProduct.bySku(skuOpt.get())
                    .map(mapper::toResponse)
                    .flatMap(dto -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).bodyValue(dto))
                    .switchIfEmpty(ServerResponse.notFound().build());
        }

        if (nameOpt.isPresent()) {
            final int page = serverRequest.queryParam("page").map(Integer::parseInt).orElse(0);
            final int pageSize = 10; // Tamaño de página estático
            log.info("[searchProducts] Buscando por nombre: '{}' en la página: {}", nameOpt.get(), page);
            return repositorySearchPaginatedProducts.byName(nameOpt.get(), page, pageSize)
                    .flatMap(pageResult -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).bodyValue(pageResult));
        }

        return ServerResponse.badRequest()
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(Map.of("error", "Se requiere un criterio de búsqueda válido (id, sku, o name)."));
    }

    public Mono<ServerResponse> updateProduct(ServerRequest serverRequest) {
        final Long id = Long.parseLong(serverRequest.pathVariable("id"));
        log.info("[updateProduct] Actualizando producto con ID: {} ", id);

        return serverRequest.bodyToMono(UpdateProductDTO.class)
                .flatMap(dto -> repositoryUpdateProduct.update(id, dto.price(), dto.description()))
                .map(mapper::toResponse)
                .flatMap(responseDto -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(Map.of("update", "Se ha actualizado de forma exitosa")))
                .onErrorResume(RuntimeException.class, error ->
                        ServerResponse.badRequest()
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(Map.of("error", error.getMessage())));
    }

    public Mono<ServerResponse> deleteProductById(ServerRequest serverRequest) {
        final Long id = Long.parseLong(serverRequest.pathVariable("id"));
        log.info("[deleteProductById] Eliminando producto con id: {}", id);

        return repositoryDeleteProduct.deleteById(id)
                .then(ServerResponse.noContent().build())
                .onErrorResume(RuntimeException.class, error -> ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> addStock(ServerRequest serverRequest) {
        final Long id = Long.parseLong(serverRequest.pathVariable("id"));
        log.info("[addStock] Agregando stock al producto con ID: {}", id);

        return serverRequest.bodyToMono(AddStockDTO.class)
                .flatMap(dto -> repositoryAddStock.addStock(id, dto.quantity()))
                .map(mapper::toResponse)
                .flatMap(responseDTO -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(Map.of("update", "Se ha agregado la cantidad de producto en stock")))
                .onErrorResume(IllegalArgumentException.class, error ->
                        ServerResponse.badRequest()
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(Map.of("bad_request", error.getMessage())))
                .onErrorResume(RuntimeException.class, error ->
                        ServerResponse.status(HttpStatus.NOT_FOUND)
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(Map.of("not_found", error.getMessage())));
    }

    public Mono<ServerResponse> removeStock(ServerRequest serverRequest) {
        final Long id = Long.parseLong(serverRequest.pathVariable("id"));
        log.info("[removeStock] Registrando salida de stock para el producto ID: {}", id);

        return serverRequest.bodyToMono(RemoveStockDTO.class)
                .flatMap(dto -> {
                    log.debug("[removeStock] Recibiendo DTO: {}", dto);
                    return repositoryRemoveStock.removeStock(id, dto.quantity(), dto.type());
                })
                .map(mapper::toResponse)
                .flatMap(responseProductDTO ->
                        ServerResponse.ok()
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(responseProductDTO)
                )
                .onErrorResume(InsufficientStockException.class, error -> {
                    log.warn("[removeStock] Conflicto de stock para ID {}: {}", id, error.getMessage());
                    return ServerResponse.status(HttpStatus.CONFLICT)
                            .contentType(MediaType.APPLICATION_JSON)
                            .bodyValue(Map.of("error 409", error.getMessage()));

                })
                .onErrorResume(IllegalArgumentException.class, error -> {
                    log.warn("[removeStock] Argumento invalido para ID: {}, {}", id, error.getMessage());
                    return ServerResponse.badRequest()
                            .contentType(MediaType.APPLICATION_JSON)
                            .bodyValue(Map.of("error 400", error.getMessage()));

                })
                .onErrorResume(RuntimeException.class, error -> {
                    log.error("[removeStock] Error buscando producto ID {}: {}", id, error.getMessage());
                    return ServerResponse.status(HttpStatus.NOT_FOUND)
                            .contentType(MediaType.APPLICATION_JSON)
                            .bodyValue(Map.of("error 404", error.getMessage()));
                });
    }
}
