package co.dev.victorroe.r2dbc;

import co.dev.victorroe.model.supplier.Supplier;
import co.dev.victorroe.model.supplier.gateways.SupplierRepository;
import co.dev.victorroe.r2dbc.entity.SupplierEntity;
import co.dev.victorroe.r2dbc.helper.ReactiveAdapterOperations;
import co.dev.victorroe.r2dbc.mapper.SupplierMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Slf4j
@Repository
public class SupplierReactiveRepositoryAdapter extends ReactiveAdapterOperations<
        Supplier,
        SupplierEntity,
        Long,
        SupplierReactiveRepository
        > implements SupplierRepository {

    public SupplierReactiveRepositoryAdapter(SupplierReactiveRepository repository, SupplierMapper mapper) {
        super(repository, mapper::toEntity, mapper::toDomain);
    }

    @Override
    public Mono<Supplier> findById(Long id) {
        log.info("Buscando proovedor con id: {}", id);
        return super.findById(id)
                .doOnSuccess(supplier -> {
                    if (supplier != null) {
                        log.info("Categoria encontrada exitosamente");
                    } else {
                        log.warn("No se encontro ninguna categoria");
                    }
                })
                .doOnError(error -> log.error("Ocurrio un error al buscar la proovedor"));
    }
}
