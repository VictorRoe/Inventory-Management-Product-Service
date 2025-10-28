package co.dev.victorroe.api;

import co.dev.victorroe.api.dto.category.RequestCategoryDTO;
import co.dev.victorroe.api.dto.category.UpdateCategoryDTO;
import co.dev.victorroe.api.mapper.CategoryDTOMapper;
import co.dev.victorroe.usecase.category.CreateCategoryUseCase;
import co.dev.victorroe.usecase.category.DeleteCategoryUseCase;
import co.dev.victorroe.usecase.category.FindCategoryByIdUseCase;
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
    private final FindCategoryByIdUseCase repositoryFindCategoryById;
    private final UpdateCategoryUseCase repositoryUpdateCategory;
    private final DeleteCategoryUseCase repositoryDeleteCategory;
    private final CategoryDTOMapper mapper;

    public Mono<ServerResponse> createCategory (ServerRequest serverRequest){
        return serverRequest.bodyToMono(RequestCategoryDTO.class)
                .doOnNext(dto -> log.info("[createCategory] creando categoria: {}", dto))
                .map(mapper::toRequest)
                .flatMap(repositoryCreate::createCategory)
                .doOnSuccess(saved -> log.info("[createCategory] Categoria creado exitosamente: {}", saved))
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

    public Mono<ServerResponse> findCategoryById(ServerRequest serverRequest){
        Long id = Long.parseLong(serverRequest.pathVariable("id"));
        return repositoryFindCategoryById.findCategoryById(id)
                .doOnNext(category -> log.info("[findCategoryById] Buscando categoria con ID: {}", category.getId()))
                .doOnSuccess(category -> log.info("[findCategoryById] Se ha encontrado la categoria"))
                .map(mapper::toResponse)
                .flatMap(dto -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(dto))
                .switchIfEmpty(ServerResponse.notFound().build())
                .onErrorResume(error -> ServerResponse.badRequest()
                        .contentType(MediaType.APPLICATION_JSON).build())
                .doOnError(err -> log.error("[findCategoryById] Categoria no encontrado: {}", err.getMessage()));
    }

    public Mono<ServerResponse> updateCategory (ServerRequest serverRequest){
        final Long id = Long.parseLong(serverRequest.pathVariable("id"));
        log.info("[updateCategory] Actualizando producto con ID: {}", id);

        return serverRequest.bodyToMono(UpdateCategoryDTO.class)
                .flatMap(dto -> repositoryUpdateCategory.update(id, dto.name()))
                .map(mapper::toResponse)
                .flatMap(responseCategoryDTO -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(Map.of("update", "Se ha actualizado correctamente")))
                .onErrorResume(RuntimeException.class, error ->
                        ServerResponse.badRequest()
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(Map.of("update", "Hubo un error al actualizar categoria")));
    }

    public Mono<ServerResponse> deleteCategoryById (ServerRequest serverRequest) {
        final Long id = Long.parseLong(serverRequest.pathVariable("id"));
        log.info("[deleteCategoryById] Eliminando Categoria con ID: {}", id);
        return repositoryDeleteCategory.deleteCategoryById(id)
                .then(ServerResponse.noContent().build())
                .onErrorResume(RuntimeException.class, error -> ServerResponse.notFound().build());
    }


}
