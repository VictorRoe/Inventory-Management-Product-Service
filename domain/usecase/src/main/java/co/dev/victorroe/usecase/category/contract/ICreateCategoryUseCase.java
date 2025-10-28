package co.dev.victorroe.usecase.category.contract;

import co.dev.victorroe.model.category.Category;
import reactor.core.publisher.Mono;

public interface ICreateCategoryUseCase {

    Mono<Category> createCategory(Category category);
}
