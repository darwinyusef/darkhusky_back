package com.darkhusky.darkhusky_back.infrastructure.persistence.jpa.repository;

import com.darkhusky.darkhusky_back.infrastructure.persistence.jpa.entity.ShippingAddressEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ShippingAddressJpaRepository extends JpaRepository<ShippingAddressEntity, Long> {
    Optional<ShippingAddressEntity> findByOrderId(Long orderId);
}
