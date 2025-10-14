package co.dev.victorroe.api;

import co.dev.victorroe.api.dto.RequestProductDTO;
import co.dev.victorroe.api.mapper.ProductDTOMapper;
import co.dev.victorroe.usecase.product.ProductUseCase;
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

    private final ProductUseCase productUseCase;
    private final ProductDTOMapper mapper;

    public Mono<ServerResponse> createProduct(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(RequestProductDTO.class)
                .doOnNext(dto -> log.info("[createProduct] Creando producto: {}", dto))
                .map(mapper::toRequest)
                .flatMap(productUseCase::create)
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
}
