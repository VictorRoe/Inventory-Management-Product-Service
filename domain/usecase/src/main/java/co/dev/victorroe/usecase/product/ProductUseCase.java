package co.dev.victorroe.usecase.product;

import co.dev.victorroe.model.product.Product;
import co.dev.victorroe.model.product.gateways.ProductRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class ProductUseCase implements IProductUseCase {

    private final ProductRepository productRepository;

    @Override
    public Mono<Product> create(Product product) {
        return productRepository.create(product);
    }
}
