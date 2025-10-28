package co.dev.victorroe.api;

import co.dev.victorroe.api.dto.category.RequestCategoryDTO;
import co.dev.victorroe.api.mapper.CategoryDTOMapper;
import co.dev.victorroe.usecase.category.CreateCategoryUseCase;
import co.dev.victorroe.usecase.category.DeleteCategoryUseCase;
import co.dev.victorroe.usecase.category.FindCategoryById;
import co.dev.victorroe.usecase.category.UpdateCategoryUseCase;
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
public class CategoryHandler {

    private final CreateCategoryUseCase repositoryCreate;
    private final FindCategoryById repositoryFindCategoryById;
    private final UpdateCategoryUseCase repositoryUpdateCategory;
    private final DeleteCategoryUseCase repositoryDeleteCategory;
    private final CategoryDTOMapper mapper;

    public Mono<ServerResponse> createCategory (ServerRequest serverRequest){
        return serverRequest.bodyToMono(RequestCategoryDTO.class)
                .doOnNext(dto -> log.info("[createCategory] creando categoria: {}", dto))
                .map(mapper::toRequest)
                .flatMap(repositoryCreate::createCategory)
                .doOnSuccess(saved -> log.info("[createCategory] Producto creado exitosamente: {}", saved))
                .map(mapper::toResponse)
                .flatMap(dto -> ServerResponse.status(HttpStatus.CREATED)
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(Map.of("created", "Se ha creado la categoria"))
                )
                .onErrorResume(IllegalArgumentException.class, error ->{
                    log.error("[createCategory] Error al crear la categoria: {}", error.getMessage());
                    return ServerResponse.badRequest()
                            .contentType(MediaType.APPLICATION_JSON)
                            .bodyValue(Map.of("error", "error al crear categoria"));
                });
    }


}
