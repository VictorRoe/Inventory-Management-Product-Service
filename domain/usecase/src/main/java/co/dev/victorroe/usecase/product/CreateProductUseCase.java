package co.dev.victorroe.usecase.product;

import co.dev.victorroe.model.inventorymovement.InventoryMovement;
import co.dev.victorroe.model.inventorymovement.TransactionType;
import co.dev.victorroe.model.inventorymovement.gateways.InventoryMovementRepository;
import co.dev.victorroe.model.product.Product;
import co.dev.victorroe.model.product.gateways.ProductRepository;
import co.dev.victorroe.usecase.product.contract.ICreateProductUseCase;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.logging.Logger;

@RequiredArgsConstructor
public class CreateProductUseCase implements ICreateProductUseCase {

    private final ProductRepository repository;
    private final InventoryMovementRepository movementRepository;
    private final Logger log = Logger.getLogger(CreateProductUseCase.class.getName());

    @Override
    public Mono<Product> create(Product product) {
        log.info("[CreateProductUseCase] Creando producto");
        return repository.create(product)
                .doOnSuccess(createProduct -> {
                    InventoryMovement movement = InventoryMovement.builder()
                            .productId(createProduct.getId())
                            .transactionType(TransactionType.PRODUCT_CREATED)
                            .quantity(createProduct.getStock())
                            .transactionTime(LocalDateTime.now())
                            .userId("system-user") // Auth Microservice
                            .message("Producto creado en el inventario.")
                            .build();

                    movementRepository.notifyMovement(movement)
                            .doOnError(e -> log.warning("Error al publicar evento SQS para producto creado" + e.getMessage()))
                            .subscribe();
                });

    }
}
