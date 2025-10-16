package co.dev.victorroe.r2dbc.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;

    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Data
    @Table(name = "product")
public class ProductEntity {

    @Id
    private Long id;
    @Column("name")
    private String name;
    @Column("description")
    private String description;
    @Column("price")
    private BigDecimal price;
    @Column("stock")
    private Long stock;
    @Column("sku")
    private String sku;
    @Column("category_id")
    private Long categoryId;
    @Column("supplier_id")
    private Long supplierId;

}
