package co.dev.victorroe.r2dbc;

import co.dev.victorroe.r2dbc.entity.ProductEntity;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;


public interface ProductReactiveRepository extends ReactiveCrudRepository<ProductEntity, Long>, ReactiveQueryByExampleExecutor<ProductEntity> {

}
