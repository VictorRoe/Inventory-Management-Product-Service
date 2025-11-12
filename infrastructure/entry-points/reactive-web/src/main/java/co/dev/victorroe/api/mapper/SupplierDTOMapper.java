package co.dev.victorroe.api.mapper;

import co.dev.victorroe.api.dto.supplier.RequestSupplierDTO;
import co.dev.victorroe.api.dto.supplier.ResponseSupplierDTO;
import co.dev.victorroe.model.supplier.Supplier;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SupplierDTOMapper {

    Supplier toRequest(RequestSupplierDTO requestSupplierDTO);

    ResponseSupplierDTO toResponse(Supplier supplier);
}
