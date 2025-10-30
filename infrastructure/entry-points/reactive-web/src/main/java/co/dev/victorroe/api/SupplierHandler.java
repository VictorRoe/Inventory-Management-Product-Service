package co.dev.victorroe.api;

import co.dev.victorroe.api.dto.category.UpdateCategoryDTO;
import co.dev.victorroe.api.dto.supplier.RequestSupplierDTO;
import co.dev.victorroe.api.dto.supplier.UpdateSupplierDTO;
import co.dev.victorroe.api.mapper.SupplierDTOMapper;
import co.dev.victorroe.usecase.supplier.CreateSupplierUseCase;
import co.dev.victorroe.usecase.supplier.DeleteSupplierUseCase;
import co.dev.victorroe.usecase.supplier.FindSupplierByIdUseCase;
import co.dev.victorroe.usecase.supplier.UpdateSupplierUseCase;
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
public class SupplierHandler {

    private final CreateSupplierUseCase repositoryCreate;
    private final FindSupplierByIdUseCase repositoryFindSupplierById;
    private final UpdateSupplierUseCase repositoryUpdateSupplier;
    private final DeleteSupplierUseCase repositoryDeleteSupplier;
    private final SupplierDTOMapper mapper;

    public Mono<ServerResponse> createSupplier (ServerRequest serverRequest){
        return serverRequest.bodyToMono(RequestSupplierDTO.class)
                .doOnNext(dto -> log.info("[createSupplier] creando proovedor: {}", dto))
                .map(mapper::toRequest)
                .flatMap(repositoryCreate::create)
                .doOnSuccess(saved -> log.info("[createSupplier] Proovedor creado exitosamente: {}", saved))
                .map(mapper::toResponse)
                .flatMap(dto -> ServerResponse.status(HttpStatus.CREATED)
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(Map.of("created", "Se ha creado el proovedor"))
                )
                .onErrorResume(IllegalArgumentException.class, error ->{
                    log.error("[createSupplier] Error al crear el proovedor: {}", error.getMessage());
                    return ServerResponse.badRequest()
                            .contentType(MediaType.APPLICATION_JSON)
                            .bodyValue(Map.of("error", "error al crear el proovedor"));
                });
    }

    public Mono<ServerResponse> findSupplierById(ServerRequest serverRequest){
        Long id = Long.parseLong(serverRequest.pathVariable("id"));
        return repositoryFindSupplierById.findSupplierById(id)
                .doOnNext(supplier -> log.info("[findSupplierById] Buscando supplier con ID: {}", supplier.getId()))
                .doOnSuccess(supplier -> log.info("[findSupplierById] Se ha encontrado la supplier"))
                .map(mapper::toResponse)
                .flatMap(dto -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(dto))
                .switchIfEmpty(ServerResponse.notFound().build())
                .onErrorResume(error -> ServerResponse.badRequest()
                        .contentType(MediaType.APPLICATION_JSON).build())
                .doOnError(err -> log.error("[findSupplierById] Proovedor no encontrado: {}", err.getMessage()));
    }

    public Mono<ServerResponse> updateSupplier (ServerRequest serverRequest){
        final Long id = Long.parseLong(serverRequest.pathVariable("id"));
        log.info("[updateSupplier] Actualizando proovedor con ID: {}", id);

        return serverRequest.bodyToMono(UpdateSupplierDTO.class)
                .flatMap(dto -> repositoryUpdateSupplier.updateSupplier(id, dto.name(), dto.lastName(), dto.address(), dto.company()))
                .map(mapper::toResponse)
                .flatMap(responseSupplierDTO -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(Map.of("update", "Se ha actualizado correctamente")))
                .onErrorResume(RuntimeException.class, error ->
                        ServerResponse.badRequest()
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(Map.of("update", "Hubo un error al actualizar proovedor")));
    }

    public Mono<ServerResponse> deleteSupplierById (ServerRequest serverRequest) {
        final Long id = Long.parseLong(serverRequest.pathVariable("id"));
        log.info("[deleteSupplierById] Eliminando proovedor con ID: {}", id);
        return repositoryDeleteSupplier.deleteSupplierById(id)
                .then(ServerResponse.noContent().build())
                .onErrorResume(RuntimeException.class, error -> ServerResponse.notFound().build());
    }
}
