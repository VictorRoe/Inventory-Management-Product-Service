package co.dev.victorroe.model.category.gateways;

import co.dev.victorroe.model.category.Category;
import reactor.core.publisher.Mono;

public interface CategoryRepository {

    Mono<Category> findById(Long id);
}
