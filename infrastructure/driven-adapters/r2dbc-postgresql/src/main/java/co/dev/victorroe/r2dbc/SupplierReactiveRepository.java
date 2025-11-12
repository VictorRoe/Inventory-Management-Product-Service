package co.dev.victorroe.r2dbc;

import co.dev.victorroe.r2dbc.entity.SupplierEntity;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

import java.util.Collection;

public interface SupplierReactiveRepository extends ReactiveCrudRepository<SupplierEntity, Long>, ReactiveQueryByExampleExecutor<SupplierEntity> {
    Flux<SupplierEntity> findAllByIdIn (Collection<Long> ids);
}
