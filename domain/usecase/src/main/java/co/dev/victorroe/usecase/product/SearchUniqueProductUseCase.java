package co.dev.victorroe.usecase.product;

import co.dev.victorroe.model.product.Product;
import co.dev.victorroe.model.product.gateways.ProductRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RequiredArgsConstructor
public class SearchUniqueProductUseCase implements ISearchUniqueProductUseCase{

    private final ProductRepository repository;
    private final List<IProductDataEnricherUseCase> enrichers;

    @Override
    public Mono<Product> byId(Long id) {
        return repository.findById(id).flatMap(this::enrichProduct);
    }

    @Override
    public Mono<Product> bySku(String sku) {
        return repository.findBySku(sku).flatMap(this::enrichProduct);
    }

    private Mono<Product> enrichProduct(Product product) {
        return Flux.fromIterable(enrichers)
                .reduce(Mono.just(product), (productMono, enricher) -> productMono.flatMap(enricher::enrich))
                .flatMap(mono -> mono);
    }
}
