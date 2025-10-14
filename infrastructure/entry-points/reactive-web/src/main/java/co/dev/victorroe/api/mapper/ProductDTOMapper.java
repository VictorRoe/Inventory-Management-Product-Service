package co.dev.victorroe.api.mapper;

import co.dev.victorroe.api.dto.RequestProductDTO;
import co.dev.victorroe.api.dto.ResponseProductDTO;
import co.dev.victorroe.model.product.Product;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductDTOMapper {

    Product toRequest(RequestProductDTO requestProductDTO);
    ResponseProductDTO toResponse(Product product);
}
