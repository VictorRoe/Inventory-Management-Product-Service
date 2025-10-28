package co.dev.victorroe.usecase.category.contract;

import co.dev.victorroe.model.category.Category;
import reactor.core.publisher.Mono;

import java.util.Optional;

public interface IUpdateCategoryUseCase {

    Mono<Category> update (Long id, Optional<String> newName);
}
