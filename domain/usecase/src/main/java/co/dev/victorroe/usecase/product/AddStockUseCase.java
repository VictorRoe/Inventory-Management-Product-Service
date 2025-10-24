package co.dev.victorroe.usecase.product;

import static java.util.Objects.isNull;

import co.dev.victorroe.model.inventorymovement.InventoryMovement;
import co.dev.victorroe.model.inventorymovement.TransactionType;
import co.dev.victorroe.model.inventorymovement.gateways.InventoryMovementRepository;
import co.dev.victorroe.model.product.Product;
import co.dev.victorroe.model.product.gateways.ProductRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.logging.Logger;


@RequiredArgsConstructor
public class AddStockUseCase implements IAddStockUseCase {

    private final ProductRepository repository;
    private final InventoryMovementRepository movementRepository;
    private final Logger log = Logger.getLogger(AddStockUseCase.class.getName());

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
                })
                .doOnSuccess(updatedProduct -> {
                    InventoryMovement movement = InventoryMovement.builder()
                            .productId(updatedProduct.getId())
                            .transactionType(TransactionType.STOCK_ADDED)
                            .quantity(quantity)
                            .transactionTime(LocalDateTime.now())
                            .userId("system-user")
                            .message("Entrada de stock manual.")
                            .build();

                    movementRepository.notifyMovement(movement)
                            .doOnError(e -> log.warning("Error al publicar evento SQS de entrada de stock"))
                            .subscribe();
                });
    }
}
