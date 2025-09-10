package com.darkhusky.darkhusky_back.domain.port.out;

import com.darkhusky.darkhusky_back.domain.model.OrderItem;

import java.util.List;

public interface OrderItemRepositoryPort {
    OrderItem save(OrderItem orderItem);
    List<OrderItem> saveAll(List<OrderItem> orderItems);
    List<OrderItem> findByOrderId(Long orderId);
}
