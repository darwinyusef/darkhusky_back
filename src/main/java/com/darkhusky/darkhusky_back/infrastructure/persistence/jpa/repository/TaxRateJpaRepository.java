package com.darkhusky.darkhusky_back.infrastructure.persistence.jpa.repository;

import com.darkhusky.darkhusky_back.infrastructure.persistence.jpa.entity.TaxRateEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TaxRateJpaRepository extends JpaRepository<TaxRateEntity, Long> {
    Optional<TaxRateEntity> findByCountry(String country);
}
