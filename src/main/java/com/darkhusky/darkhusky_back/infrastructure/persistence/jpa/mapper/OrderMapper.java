package com.darkhusky.darkhusky_back.infrastructure.persistence.jpa.mapper;

import com.darkhusky.darkhusky_back.domain.model.Order;
import com.darkhusky.darkhusky_back.infrastructure.persistence.jpa.entity.OrderEntity;
import com.darkhusky.darkhusky_back.infrastructure.persistence.jpa.entity.UserEntity;

public class OrderMapper {

    public static Order toDomain(OrderEntity entity) {
        if (entity == null) {
            return null;
        }
        return new Order(
                entity.getId(),
                entity.getUser() != null ? entity.getUser().getUserId() : null,
                entity.getStatus(),
                entity.getTotal(),
                entity.getCreatedAt(),
                entity.getPaidAt()
        );
    }

    public static OrderEntity toEntity(Order domain) {
        if (domain == null) {
            return null;
        }
        OrderEntity entity = new OrderEntity();
        entity.setId(domain.getId());
        if (domain.getUserId() != null) {
            UserEntity user = new UserEntity();
            user.setUserId(domain.getUserId());
            entity.setUser(user);
        }
        entity.setStatus(domain.getStatus());
        entity.setTotal(domain.getTotal());
        entity.setCreatedAt(domain.getCreatedAt());
        entity.setPaidAt(domain.getPaidAt());
        return entity;
    }
}
