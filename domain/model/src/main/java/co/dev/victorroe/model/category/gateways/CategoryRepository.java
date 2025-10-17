package co.dev.victorroe.model.category.gateways;

import co.dev.victorroe.model.category.Category;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collection;

public interface CategoryRepository {

    Mono<Category> findById(Long id);
    Flux<Category> findByIdIn(Collection<Long> ids);
}
