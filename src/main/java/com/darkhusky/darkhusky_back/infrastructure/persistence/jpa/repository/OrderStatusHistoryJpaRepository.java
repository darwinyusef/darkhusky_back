package com.darkhusky.darkhusky_back.infrastructure.persistence.jpa.repository;

import com.darkhusky.darkhusky_back.infrastructure.persistence.jpa.entity.OrderStatusHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderStatusHistoryJpaRepository extends JpaRepository<OrderStatusHistoryEntity, Long> {
    List<OrderStatusHistoryEntity> findByOrderId(Long orderId);
}
