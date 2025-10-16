package co.dev.victorroe.r2dbc.mapper;

import co.dev.victorroe.model.category.Category;
import co.dev.victorroe.r2dbc.entity.CategoryEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    Category toDomain(CategoryEntity entity);
    CategoryEntity toEntity(Category category);
}
