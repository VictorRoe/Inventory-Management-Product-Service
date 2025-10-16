package co.dev.victorroe.r2dbc.mapper;

import co.dev.victorroe.model.supplier.Supplier;
import co.dev.victorroe.r2dbc.entity.SupplierEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SupplierMapper {
    Supplier toDomain (SupplierEntity entity);
    SupplierEntity toEntity (Supplier supplier);
}
