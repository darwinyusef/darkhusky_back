package com.darkhusky.darkhusky_back.application.service;

import com.darkhusky.darkhusky_back.domain.model.Order;
import com.darkhusky.darkhusky_back.domain.model.OrderItem;
import com.darkhusky.darkhusky_back.domain.model.OrderStatusHistory;
import com.darkhusky.darkhusky_back.domain.model.ShippingAddress;
import com.darkhusky.darkhusky_back.domain.port.in.CartUseCase;
import com.darkhusky.darkhusky_back.domain.port.in.OrderUseCase;
import com.darkhusky.darkhusky_back.domain.port.out.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderService implements OrderUseCase {

    private final OrderRepositoryPort orderRepositoryPort;
    private final OrderItemRepositoryPort orderItemRepositoryPort;
    private final ShippingAddressRepositoryPort shippingAddressRepositoryPort;
    private final OrderStatusHistoryRepositoryPort orderStatusHistoryRepositoryPort;
    private final CartUseCase cartUseCase;

    public OrderService(OrderRepositoryPort orderRepositoryPort, OrderItemRepositoryPort orderItemRepositoryPort, ShippingAddressRepositoryPort shippingAddressRepositoryPort, OrderStatusHistoryRepositoryPort orderStatusHistoryRepositoryPort, CartUseCase cartUseCase) {
        this.orderRepositoryPort = orderRepositoryPort;
        this.orderItemRepositoryPort = orderItemRepositoryPort;
        this.shippingAddressRepositoryPort = shippingAddressRepositoryPort;
        this.orderStatusHistoryRepositoryPort = orderStatusHistoryRepositoryPort;
        this.cartUseCase = cartUseCase;
    }

    @Override
    @Transactional
    public Order createOrderFromCart(Long userId, ShippingAddress shippingAddress) {
        // 1. Get user's cart
        return cartUseCase.getCartByUserId(userId).map(cart -> {
            List<OrderItem> orderItems = cartUseCase.getCartItems(cart.getId()).stream()
                    .map(cartItem -> new OrderItem(null, null, cartItem.getProductId(), cartItem.getQuantity(), cartItem.getUnitPrice()))
                    .collect(Collectors.toList());

            if (orderItems.isEmpty()) {
                throw new IllegalStateException("Cannot create an order from an empty cart.");
            }

            // 2. Calculate total
            BigDecimal total = orderItems.stream()
                    .map(item -> item.getUnitPrice().multiply(new BigDecimal(item.getQuantity())))
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            // 3. Create and save the order
            Order newOrder = new Order(null, userId, "PENDING", total, LocalDateTime.now(), null);
            Order savedOrder = orderRepositoryPort.save(newOrder);

            // 4. Associate items with the order and save them
            orderItems.forEach(item -> item.setOrderId(savedOrder.getId()));
            orderItemRepositoryPort.saveAll(orderItems);

            // 5. Save shipping address
            shippingAddress.setOrderId(savedOrder.getId());
            shippingAddressRepositoryPort.save(shippingAddress);

            // 6. Create initial status history
            OrderStatusHistory history = new OrderStatusHistory(null, savedOrder.getId(), "PENDING", LocalDateTime.now());
            orderStatusHistoryRepositoryPort.save(history);

            // 7. Clear the cart
            cartUseCase.clearCart(userId);

            return savedOrder;
        }).orElseThrow(() -> new IllegalStateException("User does not have an active cart."));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Order> getOrderById(Long orderId) {
        return orderRepositoryPort.findById(orderId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Order> getOrderHistoryByUserId(Long userId) {
        return orderRepositoryPort.findByUserId(userId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderItem> getOrderItems(Long orderId) {
        return orderItemRepositoryPort.findByOrderId(orderId);
    }

    @Override
    @Transactional
    public Optional<Order> updateOrderStatus(Long orderId, String status) {
        return orderRepositoryPort.findById(orderId).map(order -> {
            order.setStatus(status);
            Order updatedOrder = orderRepositoryPort.save(order);

            OrderStatusHistory history = new OrderStatusHistory(null, orderId, status, LocalDateTime.now());
            orderStatusHistoryRepositoryPort.save(history);

            return updatedOrder;
        });
    }
}
