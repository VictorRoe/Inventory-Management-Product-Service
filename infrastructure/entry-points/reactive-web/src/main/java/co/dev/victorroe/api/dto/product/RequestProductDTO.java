package co.dev.victorroe.api.dto.product;

import java.math.BigDecimal;

public record RequestProductDTO(
        String name,
        String description,
        BigDecimal price,
        Long stock,
        String sku,
        Long category_id,
        Long supplier_id
) {
}
