package co.dev.victorroe.r2dbc;

import co.dev.victorroe.r2dbc.entity.CategoryEntity;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

import java.util.Collection;

public interface CategoryReactiveRepository extends ReactiveCrudRepository<CategoryEntity, Long>, ReactiveQueryByExampleExecutor<CategoryEntity> {
    Flux<CategoryEntity> findAllByIdIn(Collection<Long> ids);
}
