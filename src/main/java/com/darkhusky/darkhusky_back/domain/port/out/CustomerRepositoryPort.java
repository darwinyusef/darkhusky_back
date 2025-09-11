package com.darkhusky.darkhusky_back.domain.port.out;

import com.darkhusky.darkhusky_back.domain.model.Customer;

import java.util.List;
import java.util.Optional;

public interface CustomerRepositoryPort {
    Customer save(Customer customer);
    Optional<Customer> findById(Long id);
    Optional<Customer> findByUserId(Long userId);
    List<Customer> findAll();
    void deleteById(Long id);
}
