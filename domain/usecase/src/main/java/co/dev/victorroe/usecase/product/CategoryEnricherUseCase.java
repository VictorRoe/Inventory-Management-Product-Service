package co.dev.victorroe.usecase.product;

import co.dev.victorroe.model.category.gateways.CategoryRepository;
import co.dev.victorroe.model.product.Product;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class CategoryEnricherUseCase implements IProductDataEnricherUseCase {

    private final CategoryRepository repository;

    @Override
    public Mono<Product> enrich(Product product) {
        if (product.getCategory() == null || product.getCategory().getId() == null) {
            return Mono.just(product);
        }
        return repository.findById(product.getCategory().getId())
                .map(fullCategory -> product.toBuilder().category(fullCategory).build())
                .defaultIfEmpty(product);
    }
}

