package com.darkhusky.darkhusky_back.infrastructure.persistence.jpa.mapper;

import com.darkhusky.darkhusky_back.domain.model.QrCode;
import com.darkhusky.darkhusky_back.infrastructure.persistence.jpa.entity.QrCodeEntity;

public class QrCodeMapper {

    public static QrCode toDomain(QrCodeEntity entity) {
        if (entity == null) return null;

        QrCode domain = new QrCode();
        domain.setId(entity.getId());
        domain.setQrCode(entity.getQrCode());
        domain.setRelatedEntityType(entity.getRelatedEntityType());
        domain.setRelatedEntityId(entity.getRelatedEntityId());
        domain.setType(entity.getType());
        domain.setStatus(entity.getStatus());
        domain.setCreatedAt(entity.getCreatedAt());
        domain.setUpdatedAt(entity.getUpdatedAt());
        return domain;
    }

    public static QrCodeEntity toEntity(QrCode domain) {
        if (domain == null) return null;

        QrCodeEntity entity = new QrCodeEntity();
        entity.setId(domain.getId());
        entity.setQrCode(domain.getQrCode());
        entity.setRelatedEntityType(domain.getRelatedEntityType());
        entity.setRelatedEntityId(domain.getRelatedEntityId());
        entity.setType(domain.getType());
        entity.setStatus(domain.getStatus());
        entity.setCreatedAt(domain.getCreatedAt());
        entity.setUpdatedAt(domain.getUpdatedAt());
        return entity;
    }
}
