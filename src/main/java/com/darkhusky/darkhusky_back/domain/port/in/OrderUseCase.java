package com.darkhusky.darkhusky_back.domain.port.in;

import com.darkhusky.darkhusky_back.domain.model.Order;
import com.darkhusky.darkhusky_back.domain.model.OrderItem;
import com.darkhusky.darkhusky_back.domain.model.ShippingAddress;

import java.util.List;
import java.util.Optional;

public interface OrderUseCase {
    // "Realizar pedidos y generar órdenes"
    Order createOrderFromCart(Long userId, ShippingAddress shippingAddress);

    Optional<Order> getOrderById(Long orderId);

    // "Gestionar usuarios y su historial de compras"
    List<Order> getOrderHistoryByUserId(Long userId);

    List<OrderItem> getOrderItems(Long orderId);

    Optional<Order> updateOrderStatus(Long orderId, String status);
}
