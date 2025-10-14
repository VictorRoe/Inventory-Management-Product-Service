package co.dev.victorroe.api.dto;

import java.math.BigDecimal;

public record RequestProductDTO(
        String name,
        String description,
        BigDecimal price,
        Long stock,
        String sku
) {
}
