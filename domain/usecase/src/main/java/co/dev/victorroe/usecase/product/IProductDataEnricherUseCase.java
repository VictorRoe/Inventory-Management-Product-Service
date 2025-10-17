package co.dev.victorroe.usecase.product;

import co.dev.victorroe.model.product.Product;
import reactor.core.publisher.Mono;

import java.util.List;

public interface IProductDataEnricherUseCase {
    Mono<Product> enrich(Product product);
    Mono<List<Product>> enrich(List<Product> products);
}
