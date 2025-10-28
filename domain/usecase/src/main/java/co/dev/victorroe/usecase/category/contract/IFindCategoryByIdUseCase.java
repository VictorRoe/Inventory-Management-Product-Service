package co.dev.victorroe.usecase.category.contract;

import co.dev.victorroe.model.category.Category;
import reactor.core.publisher.Mono;

public interface IFindCategoryByIdUseCase {

    Mono<Category> findCategoryById (Long id);
}
