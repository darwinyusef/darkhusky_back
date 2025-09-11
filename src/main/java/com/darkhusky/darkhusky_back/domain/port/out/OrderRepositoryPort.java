package com.darkhusky.darkhusky_back.domain.port.out;

import com.darkhusky.darkhusky_back.domain.model.Order;

import java.util.List;
import java.util.Optional;

public interface OrderRepositoryPort {
    Order save(Order order);
    Optional<Order> findById(Long id);
    List<Order> findByUserId(Long userId);
    List<Order> findByStatus(String status);
    List<Order> findAll();
    void deleteById(Long id);
}
