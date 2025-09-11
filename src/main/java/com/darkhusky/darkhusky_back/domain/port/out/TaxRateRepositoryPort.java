package com.darkhusky.darkhusky_back.domain.port.out;

import com.darkhusky.darkhusky_back.domain.model.TaxRate;

import java.util.Optional;

public interface TaxRateRepositoryPort {
    TaxRate save(TaxRate taxRate);
    Optional<TaxRate> findById(Long id);
    Optional<TaxRate> findByCountry(String country);
}
