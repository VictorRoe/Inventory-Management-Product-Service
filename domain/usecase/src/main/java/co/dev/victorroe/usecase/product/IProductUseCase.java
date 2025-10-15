package co.dev.victorroe.usecase.product;

import co.dev.victorroe.model.product.Product;
import reactor.core.publisher.Mono;

public interface IProductUseCase {

    Mono<Product> create (Product product);

    Mono<Product> findById(Long id);
}
