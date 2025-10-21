package co.dev.victorroe.api;

import co.dev.victorroe.api.dto.RequestProductDTO;
import co.dev.victorroe.api.dto.UpdateProductDTO;
import co.dev.victorroe.api.mapper.ProductDTOMapper;
import co.dev.victorroe.usecase.product.*;
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
public class Handler {

    private final CreateProductUseCase repositoryCreate;
    private final FindProductByIdUseCase repositoryFindById;
    private final FindAllProductUseCase repositoryFinAllProducts;
    private final SearchUniqueProductUseCase repositorySearchUniqueProduct;
    private final SearchPaginatedProductsUseCase repositorySearchPaginatedProducts;
    private final UpdateProductUseCase repositoryUpdateProduct;
    private final DeleteProductUseCase repositoryDeleteProduct;
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
}
