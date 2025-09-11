package com.darkhusky.darkhusky_back.infrastructure.persistence.jpa.mapper;

import com.darkhusky.darkhusky_back.domain.model.Category;
import com.darkhusky.darkhusky_back.infrastructure.persistence.jpa.entity.CategoryEntity;

public class CategoryMapper {

    public static Category toDomain(CategoryEntity entity) {
        if (entity == null) {
            return null;
        }
        return new Category(
                entity.getId(),
                entity.getName(),
                entity.getSlug(),
                entity.getDescription(),
                entity.getParentCategory() != null ? entity.getParentCategory().getId() : null
        );
    }

    public static CategoryEntity toEntity(Category domain) {
        if (domain == null) {
            return null;
        }
        CategoryEntity entity = new CategoryEntity();
        entity.setId(domain.getId());
        entity.setName(domain.getName());
        entity.setSlug(domain.getSlug());
        entity.setDescription(domain.getDescription());
        if (domain.getParentCategoryId() != null) {
            CategoryEntity parent = new CategoryEntity();
            parent.setId(domain.getParentCategoryId());
            entity.setParentCategory(parent);
        }
        return entity;
    }
}
