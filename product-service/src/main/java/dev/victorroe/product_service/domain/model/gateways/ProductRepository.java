package dev.victorroe.product_service.domain.model.gateways;

import dev.victorroe.product_service.domain.model.Product;
import reactor.core.publisher.Mono;

public interface ProductRepository {

    Mono<Product> registerProduct(Product product);
}
