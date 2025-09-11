package com.darkhusky.darkhusky_back.infrastructure.persistence.jpa.mapper;

import com.darkhusky.darkhusky_back.domain.model.TaxRate;
import com.darkhusky.darkhusky_back.infrastructure.persistence.jpa.entity.TaxRateEntity;

public class TaxRateMapper {

    public static TaxRate toDomain(TaxRateEntity entity) {
        if (entity == null) {
            return null;
        }
        return new TaxRate(
                entity.getId(),
                entity.getCountry(),
                entity.getPercentage()
        );
    }

    public static TaxRateEntity toEntity(TaxRate domain) {
        if (domain == null) {
            return null;
        }
        TaxRateEntity entity = new TaxRateEntity();
        entity.setId(domain.getId());
        entity.setCountry(domain.getCountry());
        entity.setPercentage(domain.getPercentage());
        return entity;
    }
}
