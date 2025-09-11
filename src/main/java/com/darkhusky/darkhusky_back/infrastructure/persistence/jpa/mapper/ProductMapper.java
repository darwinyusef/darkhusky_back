package com.darkhusky.darkhusky_back.infrastructure.persistence.jpa.mapper;

import com.darkhusky.darkhusky_back.domain.model.Product;
import com.darkhusky.darkhusky_back.infrastructure.persistence.jpa.entity.CategoryEntity;
import com.darkhusky.darkhusky_back.infrastructure.persistence.jpa.entity.ProductEntity;

public class ProductMapper {

    public static Product toDomain(ProductEntity entity) {
        if (entity == null) {
            return null;
        }
        return new Product(
                entity.getId(),
                entity.getName(),
                entity.getDescription(),
                entity.getPrice(),
                entity.getStock(),
                entity.getCategory() != null ? entity.getCategory().getId() : null,
                entity.getStatus(),
                entity.getCreatedAt(),
                entity.getQrCodeUrl()
        );
    }

    public static ProductEntity toEntity(Product domain) {
        if (domain == null) {
            return null;
        }
        ProductEntity entity = new ProductEntity();
        entity.setId(domain.getId());
        entity.setName(domain.getName());
        entity.setDescription(domain.getDescription());
        entity.setPrice(domain.getPrice());
        entity.setStock(domain.getStock());
        if (domain.getCategoryId() != null) {
            CategoryEntity category = new CategoryEntity();
            category.setId(domain.getCategoryId());
            entity.setCategory(category);
        }
        entity.setStatus(domain.getStatus());
        entity.setCreatedAt(domain.getCreatedAt());
        entity.setQrCodeUrl(domain.getQrCodeUrl());
        return entity;
    }
}
