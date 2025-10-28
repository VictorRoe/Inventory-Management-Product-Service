package co.dev.victorroe.r2dbc;

import co.dev.victorroe.model.category.Category;
import co.dev.victorroe.model.category.gateways.CategoryRepository;
import co.dev.victorroe.r2dbc.entity.CategoryEntity;
import co.dev.victorroe.r2dbc.helper.ReactiveAdapterOperations;
import co.dev.victorroe.r2dbc.mapper.CategoryMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.reactive.TransactionalOperator;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collection;


@Slf4j
@Repository
public class CategoryReactiveRepositoryAdapter extends ReactiveAdapterOperations<
        Category,
        CategoryEntity,
        Long,
        CategoryReactiveRepository
        > implements CategoryRepository {

    private final CategoryMapper mapper;
    private final TransactionalOperator transactionalOperator;

    public CategoryReactiveRepositoryAdapter(CategoryReactiveRepository repository, CategoryMapper mapper, TransactionalOperator transactionalOperator) {
        super(repository, mapper::toEntity, mapper::toDomain);
        this.mapper = mapper;
        this.transactionalOperator = transactionalOperator;
    }

    @Override
    public Mono<Category> findById(Long id) {
        log.info("Buscando categoria con id: {}", id);
        return super.findById(id)
                .doOnSuccess(category -> {
                    if (category != null) {
                        log.info("Categoria encontrada exitosamente");
                    } else {
                        log.warn("No se encontro ninguna categoria");
                    }
                })
                .doOnError(error -> log.error("Ocurrio un error al buscar la categoria"));
    }

    @Override
    public Flux<Category> findByIdIn(Collection<Long> ids) {
        return repository.findAllByIdIn(ids).map(mapper::toDomain);
    }

    @Override
    public Mono<Category> create(Category category) {
        return save(category).as(transactionalOperator::transactional);
    }

    @Override
    public Mono<Void> delete(Long id) {
        return repository.deleteById(id);
    }

    @Override
    public Mono<Category> update(Category category) {
        return save(category).as(transactionalOperator::transactional);
    }


}
