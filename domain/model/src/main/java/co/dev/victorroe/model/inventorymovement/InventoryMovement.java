package co.dev.victorroe.model.inventorymovement;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class InventoryMovement {

    private Long id;
    private Long productId;
    private TransactionType transactionType;
    private Long quantity;
    private LocalDateTime transactionTime;
    private String userId;
    private String message;

}
