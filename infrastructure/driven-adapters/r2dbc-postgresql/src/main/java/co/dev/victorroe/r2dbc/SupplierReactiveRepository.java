package co.dev.victorroe.r2dbc;

import co.dev.victorroe.r2dbc.entity.SupplierEntity;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface SupplierReactiveRepository extends ReactiveCrudRepository<SupplierEntity, Long>, ReactiveQueryByExampleExecutor<SupplierEntity> {
}
