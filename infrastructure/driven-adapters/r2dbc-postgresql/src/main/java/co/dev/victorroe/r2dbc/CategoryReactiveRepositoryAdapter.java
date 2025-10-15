package co.dev.victorroe.r2dbc;

import co.dev.victorroe.model.category.Category;
import co.dev.victorroe.model.category.gateways.CategoryRepository;
import co.dev.victorroe.r2dbc.entity.CategoryEntity;
import co.dev.victorroe.r2dbc.helper.ReactiveAdapterOperations;
import co.dev.victorroe.r2dbc.mapper.CategoryMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;



@Slf4j
@Repository
public class CategoryReactiveRepositoryAdapter extends ReactiveAdapterOperations<
        Category,
        CategoryEntity,
        Long,
        CategoryReactiveRepository
        > implements CategoryRepository {

    public CategoryReactiveRepositoryAdapter(CategoryReactiveRepository repository, CategoryMapper mapper) {
        super(repository, mapper::toEntity, mapper::toDomain);
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


}
