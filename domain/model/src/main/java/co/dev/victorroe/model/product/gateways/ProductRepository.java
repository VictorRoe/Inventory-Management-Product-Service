package co.dev.victorroe.model.product.gateways;

import co.dev.victorroe.model.product.Product;
import reactor.core.publisher.Mono;

public interface ProductRepository {

    Mono<Product> create(Product product);



}
