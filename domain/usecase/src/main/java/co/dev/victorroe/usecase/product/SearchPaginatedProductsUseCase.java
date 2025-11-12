package co.dev.victorroe.usecase.product;

import co.dev.victorroe.model.product.Page;
import co.dev.victorroe.model.product.Product;
import co.dev.victorroe.model.product.gateways.ProductRepository;
import co.dev.victorroe.usecase.product.contract.IProductDataEnricherUseCase;
import co.dev.victorroe.usecase.product.contract.ISearchPaginatedProductsUseCase;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RequiredArgsConstructor
public class SearchPaginatedProductsUseCase implements ISearchPaginatedProductsUseCase {

    private final ProductRepository repository;
    private final List<IProductDataEnricherUseCase> enrichers;

    @Override
    public Mono<Page<Product>> byName(String name, int page, int size) {
        return repository.findByNameContaining(name, page, size).flatMap(this::enrichPageContent);
    }

    private Mono<Page<Product>> enrichPageContent(Page<Product> productPage) {
        if (productPage.getContent() == null || productPage.getContent().isEmpty()) {
            return Mono.just(productPage);
        }
        return Flux.fromIterable(enrichers)
                .reduce(Mono.just(productPage.getContent()), (productsMono, enricher) -> productsMono.flatMap(enricher::enrich))
                .flatMap(enrichedListMono -> enrichedListMono)
                .map(enrichedList -> {
                    productPage.setContent(enrichedList);
                    return productPage;
                });
    }
}
