package co.dev.victorroe.api.dto.product;

import java.math.BigDecimal;
import java.util.Optional;

public record UpdateProductDTO(
        Optional<BigDecimal> price,
        Optional<String> description

) {
}
