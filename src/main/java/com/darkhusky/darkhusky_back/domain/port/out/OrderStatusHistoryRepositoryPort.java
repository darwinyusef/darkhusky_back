package com.darkhusky.darkhusky_back.domain.port.out;

import com.darkhusky.darkhusky_back.domain.model.OrderStatusHistory;

import java.util.List;

public interface OrderStatusHistoryRepositoryPort {
    OrderStatusHistory save(OrderStatusHistory orderStatusHistory);
    List<OrderStatusHistory> findByOrderId(Long orderId);
}
