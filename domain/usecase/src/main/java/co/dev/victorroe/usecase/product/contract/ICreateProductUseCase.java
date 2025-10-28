package co.dev.victorroe.usecase.product.contract;

import co.dev.victorroe.model.product.Product;
import reactor.core.publisher.Mono;

public interface ICreateProductUseCase {
    Mono<Product> create(Product product);
}
