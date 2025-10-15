package co.dev.victorroe.usecase.product;

import co.dev.victorroe.model.category.gateways.CategoryRepository;
import co.dev.victorroe.model.product.Product;
import co.dev.victorroe.model.product.gateways.ProductRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;
import java.util.logging.Logger;

@RequiredArgsConstructor
public class ProductUseCase implements IProductUseCase {

    private final Logger log = Logger.getLogger(ProductUseCase.class.getName());
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public Mono<Product> create(Product product) {
        log.info("[UseCase] Creando producto");
        return productRepository.create(product);
    }

    @Override
    public Mono<Product> findById(Long id) {
        log.info("[UseCase] Buscando producto");
        return productRepository.findById(id)
                .flatMap(this::enrichProductWithCategory);
    }

    private Mono<Product> enrichProductWithCategory(Product product) {
        if (product.getCategory() == null || product.getCategory().getId() == null) {
            return Mono.just(product);
        }
        return categoryRepository.findById(product.getCategory().getId())
                .map(fullCategory -> {
                    product.setCategory(fullCategory);
                    return product;
                })
                .defaultIfEmpty(product);
    }
}
