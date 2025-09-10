package com.darkhusky.darkhusky_back.infrastructure.persistence.jpa.mapper;

import com.darkhusky.darkhusky_back.domain.model.CartItem;
import com.darkhusky.darkhusky_back.infrastructure.persistence.jpa.entity.CartItemEntity;
import com.darkhusky.darkhusky_back.infrastructure.persistence.jpa.entity.CartSessionEntity;
import com.darkhusky.darkhusky_back.infrastructure.persistence.jpa.entity.ProductEntity;

public class CartItemMapper {

    public static CartItem toDomain(CartItemEntity entity) {
        if (entity == null) {
            return null;
        }
        return new CartItem(
                entity.getId(),
                entity.getCartSession() != null ? entity.getCartSession().getId() : null,
                entity.getProduct() != null ? entity.getProduct().getId() : null,
                entity.getQuantity(),
                entity.getUnitPrice()
        );
    }

    public static CartItemEntity toEntity(CartItem domain) {
        if (domain == null) {
            return null;
        }
        CartItemEntity entity = new CartItemEntity();
        entity.setId(domain.getId());
        if (domain.getCartId() != null) {
            CartSessionEntity cart = new CartSessionEntity();
            cart.setId(domain.getCartId());
            entity.setCartSession(cart);
        }
        if (domain.getProductId() != null) {
            ProductEntity product = new ProductEntity();
            product.setId(domain.getProductId());
            entity.setProduct(product);
        }
        entity.setQuantity(domain.getQuantity());
        entity.setUnitPrice(domain.getUnitPrice());
        return entity;
    }
}
