package co.dev.victorroe.r2dbc.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Table(name = "supplier")
public class SupplierEntity {

    @Id
    private Long id;
    @Column("name")
    private String name;
    @Column("last_name")
    private String lastName;
    @Column("address")
    private String address;
    @Column("company")
    private String company;
}
