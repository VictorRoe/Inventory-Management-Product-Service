package co.dev.victorroe.api.mapper;

import co.dev.victorroe.api.dto.RequestProductDTO;
import co.dev.victorroe.api.dto.ResponseProductDTO;
import co.dev.victorroe.api.dto.category.CategoryDTO;
import co.dev.victorroe.model.category.Category;
import co.dev.victorroe.model.product.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface ProductDTOMapper {

    @Mapping(target = "category", source = "category_id", qualifiedByName = "mapCategoryIdToCategory")
    Product toRequest(RequestProductDTO requestProductDTO);

    @Mapping(target = "category", source = "category")
    ResponseProductDTO toResponse(Product product);

    default CategoryDTO mapCategoryToCategoryDTO(Category category) {
        if (category == null || category.getId() == null) return null;
        return new CategoryDTO(category.getId(), category.getName());
    }

    @Named("mapCategoryIdToCategory")
    default Category mapCategoryIdToCategory(Long categoryId) {
        if (categoryId == null) return null;
        return Category.builder().id(categoryId).build();
    }
}
