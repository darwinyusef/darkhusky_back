package com.darkhusky.darkhusky_back.infrastructure.persistence.jpa.adapter;

import com.darkhusky.darkhusky_back.domain.model.TaxRate;
import com.darkhusky.darkhusky_back.domain.port.out.TaxRateRepositoryPort;
import com.darkhusky.darkhusky_back.infrastructure.persistence.jpa.entity.TaxRateEntity;
import com.darkhusky.darkhusky_back.infrastructure.persistence.jpa.mapper.TaxRateMapper;
import com.darkhusky.darkhusky_back.infrastructure.persistence.jpa.repository.TaxRateJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class TaxRateRepositoryAdapter implements TaxRateRepositoryPort {

    private final TaxRateJpaRepository taxRateJpaRepository;

    public TaxRateRepositoryAdapter(TaxRateJpaRepository taxRateJpaRepository) {
        this.taxRateJpaRepository = taxRateJpaRepository;
    }

    @Override
    public TaxRate save(TaxRate taxRate) {
        TaxRateEntity entity = TaxRateMapper.toEntity(taxRate);
        return TaxRateMapper.toDomain(taxRateJpaRepository.save(entity));
    }

    @Override
    public Optional<TaxRate> findById(Long id) {
        return taxRateJpaRepository.findById(id).map(TaxRateMapper::toDomain);
    }

    @Override
    public Optional<TaxRate> findByCountry(String country) {
        return taxRateJpaRepository.findByCountry(country).map(TaxRateMapper::toDomain);
    }
}
