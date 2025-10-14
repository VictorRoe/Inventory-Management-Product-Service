package co.dev.victorroe.usecase.product;

import co.dev.victorroe.model.product.Product;
import co.dev.victorroe.model.product.gateways.ProductRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;
import java.util.logging.Logger;

@RequiredArgsConstructor
public class ProductUseCase implements IProductUseCase {

    private final Logger log = Logger.getLogger(ProductUseCase.class.getName());
    private final ProductRepository productRepository;

    @Override
    public Mono<Product> create(Product product) {
        log.info("[create] Creando producto");
        return productRepository.create(product);
    }
}
