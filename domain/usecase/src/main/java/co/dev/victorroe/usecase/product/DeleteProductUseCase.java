package co.dev.victorroe.usecase.product;

import co.dev.victorroe.model.inventorymovement.InventoryMovement;
import co.dev.victorroe.model.inventorymovement.TransactionType;
import co.dev.victorroe.model.inventorymovement.gateways.InventoryMovementRepository;
import co.dev.victorroe.model.product.gateways.ProductRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.logging.Logger;

@RequiredArgsConstructor
public class DeleteProductUseCase implements IDeleteProductUseCase{

    private final ProductRepository repository;
    private final InventoryMovementRepository movementRepository;
    private final Logger log = Logger.getLogger(DeleteProductUseCase.class.getName());

    @Override
    public Mono<Void> deleteById(Long id) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new RuntimeException("Producto no encontrado")))
                .flatMap(product -> {
                    InventoryMovement movement = InventoryMovement.builder()
                            .productId(product.getId())
                            .transactionType(TransactionType.PRODUCT_DELETED)
                            .quantity(0L)
                            .transactionTime(LocalDateTime.now())
                            .userId("system-user")
                            .message("Producto eliminado del sistema.")
                            .build();

                    return repository.delete(product.getId())
                            .then(movementRepository.notifyMovement(movement)
                                    .doOnError(e -> log.warning("Error al publicar evento SQS de eliminación"))
                            );
                })
                .then();
    }
}
