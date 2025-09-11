package com.darkhusky.darkhusky_back.infrastructure.persistence.jpa.mapper;

import com.darkhusky.darkhusky_back.domain.model.OrderItem;
import com.darkhusky.darkhusky_back.infrastructure.persistence.jpa.entity.OrderEntity;
import com.darkhusky.darkhusky_back.infrastructure.persistence.jpa.entity.OrderItemEntity;
import com.darkhusky.darkhusky_back.infrastructure.persistence.jpa.entity.ProductEntity;

public class OrderItemMapper {

    public static OrderItem toDomain(OrderItemEntity entity) {
        if (entity == null) {
            return null;
        }
        return new OrderItem(
                entity.getId(),
                entity.getOrder() != null ? entity.getOrder().getId() : null,
                entity.getProduct() != null ? entity.getProduct().getId() : null,
                entity.getQuantity(),
                entity.getUnitPrice()
        );
    }

    public static OrderItemEntity toEntity(OrderItem domain) {
        if (domain == null) {
            return null;
        }
        OrderItemEntity entity = new OrderItemEntity();
        entity.setId(domain.getId());
        if (domain.getOrderId() != null) {
            OrderEntity order = new OrderEntity();
            order.setId(domain.getOrderId());
            entity.setOrder(order);
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
