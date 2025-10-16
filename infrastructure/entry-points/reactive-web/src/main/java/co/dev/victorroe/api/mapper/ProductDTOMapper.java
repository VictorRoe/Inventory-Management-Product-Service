package co.dev.victorroe.api.mapper;

import co.dev.victorroe.api.dto.RequestProductDTO;
import co.dev.victorroe.api.dto.ResponseProductDTO;
import co.dev.victorroe.api.dto.category.CategoryDTO;
import co.dev.victorroe.model.category.Category;
import co.dev.victorroe.model.product.Product;
import co.dev.victorroe.model.supplier.Supplier;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface ProductDTOMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "category", source = "category_id", qualifiedByName = "mapCategoryIdToCategory")
    @Mapping(target = "supplier", source = "supplier_id", qualifiedByName = "mapIdToSupplier")
    Product toRequest(RequestProductDTO requestProductDTO);

    @Mapping(target = "category", source = "category")
    @Mapping(target = "supplier", source = "supplier")
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

    @Named("mapIdToSupplier")
    default Supplier mapIdToSupplier(Long supplierId) {
        return supplierId == null ? null : Supplier.builder().id(supplierId).build();
    }
}
