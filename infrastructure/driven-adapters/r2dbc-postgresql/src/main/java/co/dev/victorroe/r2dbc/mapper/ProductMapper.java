package co.dev.victorroe.r2dbc.mapper;

import co.dev.victorroe.model.category.Category;
import co.dev.victorroe.model.product.Product;
import co.dev.victorroe.r2dbc.entity.ProductEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mapping(target = "category", source = "categoryId", qualifiedByName = "mapCategoryIdToCategory")
    Product toDomain(ProductEntity entity);

    @Mapping(target = "categoryId", source = "category.id")
    ProductEntity toEntity(Product product);

    @Named("mapCategoryIdToCategory")
    default Category mapCategoryIdToCategory(Long categoryId) {
        return categoryId == null ? null : Category.builder().id(categoryId).build();
    }
}
