package co.dev.victorroe.usecase.product;

import co.dev.victorroe.model.inventorymovement.InventoryMovement;
import co.dev.victorroe.model.inventorymovement.TransactionType;
import co.dev.victorroe.model.inventorymovement.gateways.InventoryMovementRepository;
import co.dev.victorroe.model.product.Product;
import co.dev.victorroe.model.product.gateways.ProductRepository;
import co.dev.victorroe.usecase.product.contract.IUpdateProductUseCase;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.logging.Logger;

@RequiredArgsConstructor
public class UpdateProductUseCase implements IUpdateProductUseCase {

    private final ProductRepository repository;
    private final InventoryMovementRepository movementRepository;
    private final Logger log = Logger.getLogger(UpdateProductUseCase.class.getName());

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
                })

                .doOnSuccess(updateProduct -> {
                    InventoryMovement movement = InventoryMovement.builder()
                            .productId(updateProduct.getId())
                            .transactionType(TransactionType.PRODUCT_UPDATED)
                            .quantity(0L)
                            .transactionTime(LocalDateTime.now())
                            .userId("system-user")
                            .message("Actualización de datos precio: " + updateProduct.getPrice() + " | Descripcion: " +  updateProduct.getDescription())
                            .build();


                    movementRepository.notifyMovement(movement)
                            .doOnError(e -> log.warning("Error al publicar evento SQS de actualización"))
                            .subscribe();
                });
    }
}
