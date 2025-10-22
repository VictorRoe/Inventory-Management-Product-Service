package co.dev.victorroe.usecase.product;

import static java.util.Objects.isNull;

import co.dev.victorroe.model.product.Product;
import co.dev.victorroe.model.product.StockOutputType;
import co.dev.victorroe.model.product.gateways.ProductRepository;
import co.dev.victorroe.usecase.product.exception.InsufficientStockException;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;


@RequiredArgsConstructor
public class RemoveStockUseCase implements IRemoveStockUseCase{

    private final ProductRepository repository;

    @Override
    public Mono<Product> removeStock(Long id, Long quantity, StockOutputType type) {

        if (isNull(quantity) || quantity <= 0){
            return Mono.error(new IllegalArgumentException("La cantidad a restar debe ser mayor que cero"));
        }

        return repository.findById(id)
                .switchIfEmpty(Mono.error(new RuntimeException("Producto no encontrado con ID: " + id)))
                .flatMap(ifProductExist -> {
                    if (ifProductExist.getStock() < quantity){
                        return Mono.error(new InsufficientStockException("Stock insuficiente. Disponible: " + ifProductExist.getStock()));
                    }

                    long newStock = ifProductExist.getStock() - quantity;

                    Product updateProduct = ifProductExist.toBuilder()
                            .stock(newStock)
                            .build();

                    return repository.update(updateProduct);
                });
    }
}
