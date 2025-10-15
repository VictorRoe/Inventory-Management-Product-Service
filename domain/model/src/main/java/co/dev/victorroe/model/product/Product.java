package co.dev.victorroe.model.product;
import co.dev.victorroe.model.category.Category;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Product {

    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private Long stock;
    private String sku;
    private Category category;
}
