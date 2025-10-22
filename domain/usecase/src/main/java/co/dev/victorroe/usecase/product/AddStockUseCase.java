package co.dev.victorroe.usecase.product;

import static java.util.Objects.isNull;

import co.dev.victorroe.model.product.Product;
import co.dev.victorroe.model.product.gateways.ProductRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;


@RequiredArgsConstructor
public class AddStockUseCase implements IAddStockUseCase {

    private final ProductRepository repository;

    @Override
    public Mono<Product> addStock(Long id, Long quantity) {

        if (isNull(quantity) || quantity <= 0) {
            return Mono.error(new IllegalArgumentException("La cantidad a añadir debe ser mayor a cero"));
        }

        return repository.findById(id)
                .switchIfEmpty(Mono.error(new RuntimeException("Producto no encontrado con id: " + id)))
                .flatMap(ifProductExist -> {
                    long newStock = ifProductExist.getStock() + quantity;

                    Product updateProduct = ifProductExist.toBuilder()
                            .stock(newStock)
                            .build();

                    return repository.update(updateProduct);
                });
    }
}
