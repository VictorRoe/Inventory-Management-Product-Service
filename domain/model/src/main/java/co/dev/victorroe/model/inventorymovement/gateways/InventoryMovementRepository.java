package co.dev.victorroe.model.inventorymovement.gateways;

import co.dev.victorroe.model.inventorymovement.InventoryMovement;
import reactor.core.publisher.Mono;

public interface InventoryMovementRepository {

    Mono<Void> notifyMovement(InventoryMovement movement);
}
