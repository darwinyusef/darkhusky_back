package com.darkhusky.darkhusky_back.infrastructure.persistence.jpa.repository;

import com.darkhusky.darkhusky_back.infrastructure.persistence.jpa.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderJpaRepository extends JpaRepository<OrderEntity, Long> {
    List<OrderEntity> findByUserUserId(Long userId);
    List<OrderEntity> findByStatus(String status);
}
