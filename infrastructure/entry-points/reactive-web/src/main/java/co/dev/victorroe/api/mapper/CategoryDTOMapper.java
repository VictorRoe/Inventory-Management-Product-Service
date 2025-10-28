package co.dev.victorroe.api.mapper;

import co.dev.victorroe.api.dto.category.RequestCategoryDTO;
import co.dev.victorroe.api.dto.category.ResponseCategoryDTO;
import co.dev.victorroe.model.category.Category;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryDTOMapper {

    Category toRequest(RequestCategoryDTO requestCategoryDTO);

    ResponseCategoryDTO toResponse(Category category);



}
