package co.dev.victorroe.usecase.category.contract;

import reactor.core.publisher.Mono;

public interface IDeleteCategoryUseCase {

    Mono<Void> deleteCategoryById(Long id);
}
