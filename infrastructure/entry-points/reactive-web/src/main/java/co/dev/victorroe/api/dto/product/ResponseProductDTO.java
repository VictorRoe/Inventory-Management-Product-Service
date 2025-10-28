package co.dev.victorroe.api.dto.product;

import co.dev.victorroe.api.dto.category.CategoryDTO;
import co.dev.victorroe.api.dto.supplier.SupplierDTO;

import java.math.BigDecimal;

public record ResponseProductDTO(
        Long id,
        String name,
        String description,
        BigDecimal price,
        Long stock,
        String sku,
        CategoryDTO category,
        SupplierDTO supplier
) {
}
