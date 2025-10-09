package dev.victorroe.product_service.domain.model;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Product {
    
    private String id;
    private String sku;
    private String name;
    private String description;
    private BigDecimal price;
    private Integer quantity;
}
