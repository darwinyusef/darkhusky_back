package com.darkhusky.darkhusky_back.infrastructure.persistence.jpa.adapter;

import com.darkhusky.darkhusky_back.domain.model.Customer;
import com.darkhusky.darkhusky_back.domain.port.out.CustomerRepositoryPort;
import com.darkhusky.darkhusky_back.infrastructure.persistence.jpa.entity.CustomerEntity;
import com.darkhusky.darkhusky_back.infrastructure.persistence.jpa.mapper.CustomerMapper;
import com.darkhusky.darkhusky_back.infrastructure.persistence.jpa.repository.CustomerJpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class CustomerRepositoryAdapter implements CustomerRepositoryPort {

    private final CustomerJpaRepository customerJpaRepository;

    public CustomerRepositoryAdapter(CustomerJpaRepository customerJpaRepository) {
        this.customerJpaRepository = customerJpaRepository;
    }

    @Override
    public Customer save(Customer customer) {
        CustomerEntity customerEntity = CustomerMapper.toEntity(customer);
        CustomerEntity savedEntity = customerJpaRepository.save(customerEntity);
        return CustomerMapper.toDomain(savedEntity);
    }

    @Override
    public Optional<Customer> findById(Long id) {
        return customerJpaRepository.findById(id).map(CustomerMapper::toDomain);
    }

    @Override
    public Optional<Customer> findByUserId(Long userId) {
        return customerJpaRepository.findByUserUserId(userId).map(CustomerMapper::toDomain);
    }

    @Override
    public List<Customer> findAll() {
        return customerJpaRepository.findAll().stream()
                .map(CustomerMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        customerJpaRepository.deleteById(id);
    }
}
