package co.dev.victorroe.r2dbc;

import co.dev.victorroe.r2dbc.entity.CategoryEntity;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface CategoryReactiveRepository extends ReactiveCrudRepository<CategoryEntity, Long>, ReactiveQueryByExampleExecutor<CategoryEntity> {
}
