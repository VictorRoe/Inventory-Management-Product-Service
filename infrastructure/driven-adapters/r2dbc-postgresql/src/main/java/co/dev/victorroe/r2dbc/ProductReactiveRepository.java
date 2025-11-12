package co.dev.victorroe.r2dbc;

import co.dev.victorroe.r2dbc.entity.ProductEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


public interface ProductReactiveRepository extends ReactiveCrudRepository<ProductEntity, Long>, ReactiveQueryByExampleExecutor<ProductEntity> {

    Flux<ProductEntity> findAllBy(Pageable pageable);

    Mono<ProductEntity> findBySku(String sku);

    Flux<ProductEntity> findByNameContainingIgnoreCase(String name, Pageable pageable);

    Mono<Long> countByNameContainingIgnoreCase(String name);

}
