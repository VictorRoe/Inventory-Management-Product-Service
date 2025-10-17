package co.dev.victorroe.model.product.gateways;

import co.dev.victorroe.model.product.Page;
import co.dev.victorroe.model.product.Product;
import reactor.core.publisher.Mono;

public interface ProductRepository {

    Mono<Product> create(Product product);

    Mono<Product> findById(Long id);

    Mono<Page<Product>> findAll(int page, int size);



}
