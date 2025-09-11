package com.darkhusky.darkhusky_back.infrastructure.persistence.jpa.repository;

import com.darkhusky.darkhusky_back.infrastructure.persistence.jpa.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerJpaRepository extends JpaRepository<CustomerEntity, Long> {
    Optional<CustomerEntity> findByUserUserId(Long userId);
}
