package co.dev.victorroe.api;

import co.dev.victorroe.api.dto.RequestProductDTO;
import co.dev.victorroe.api.mapper.ProductDTOMapper;
import co.dev.victorroe.usecase.product.CreateProductUseCase;
import co.dev.victorroe.usecase.product.FindProductByIdUseCase;
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
}
