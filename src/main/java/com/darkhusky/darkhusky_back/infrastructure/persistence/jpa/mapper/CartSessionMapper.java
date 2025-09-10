package com.darkhusky.darkhusky_back.infrastructure.persistence.jpa.mapper;

import com.darkhusky.darkhusky_back.domain.model.CartSession;
import com.darkhusky.darkhusky_back.infrastructure.persistence.jpa.entity.CartSessionEntity;
import com.darkhusky.darkhusky_back.infrastructure.persistence.jpa.entity.UserEntity;

public class CartSessionMapper {

    public static CartSession toDomain(CartSessionEntity entity) {
        if (entity == null) {
            return null;
        }
        return new CartSession(
                entity.getId(),
                entity.getUser() != null ? entity.getUser().getUserId() : null,
                entity.getSessionToken(),
                entity.getCreatedAt()
        );
    }

    public static CartSessionEntity toEntity(CartSession domain) {
        if (domain == null) {
            return null;
        }
        CartSessionEntity entity = new CartSessionEntity();
        entity.setId(domain.getId());
        if (domain.getUserId() != null) {
            UserEntity user = new UserEntity();
            user.setUserId(domain.getUserId());
            entity.setUser(user);
        }
        entity.setSessionToken(domain.getSessionToken());
        entity.setCreatedAt(domain.getCreatedAt());
        return entity;
    }
}
