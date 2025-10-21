package co.dev.victorroe.usecase.product;

import co.dev.victorroe.model.product.Product;
import co.dev.victorroe.model.product.gateways.ProductRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.Optional;

@RequiredArgsConstructor
public class UpdateProductUseCase implements IUpdateProductUseCase {

    private final ProductRepository repository;

    @Override
    public Mono<Product> update(Long id, Optional<BigDecimal> newPrice, Optional<String> newDescription) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new RuntimeException("Producto no encontrado con ID: " + id)))
                .flatMap(ifProductExist -> {
                    Product.ProductBuilder productBuilder = ifProductExist.toBuilder();

                    newPrice.ifPresent(productBuilder::price);
                    newDescription.ifPresent(productBuilder::description);

                    Product updateProduct = productBuilder.build();

                    return repository.update(updateProduct);
                });
    }
}
