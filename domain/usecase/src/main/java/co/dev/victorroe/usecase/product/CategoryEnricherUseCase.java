package co.dev.victorroe.usecase.product;

import co.dev.victorroe.model.category.Category;
import co.dev.victorroe.model.category.gateways.CategoryRepository;
import co.dev.victorroe.model.product.Product;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Objects;

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

    @Override
    public Mono<List<Product>> enrich(List<Product> products) {
        if (products == null || products.isEmpty()) {
            return Mono.just(products);
        }

        List<Long> categoryIds = products.stream()
                .map(p -> p.getCategory() != null ? p.getCategory().getId() : null)
                .filter(Objects::nonNull)
                .distinct()
                .toList();

        if (categoryIds.isEmpty()) {
            return Mono.just(products);
        }

        return repository.findByIdIn(categoryIds)
                .collectMap(Category::getId)
                .map(categoriesMap -> {
                    products.forEach(product -> {
                        if (product.getCategory() != null && product.getCategory().getId() != null) {
                            product.setCategory(categoriesMap.get(product.getCategory().getId()));
                        }
                    });
                    return products;
                });
    }
}

