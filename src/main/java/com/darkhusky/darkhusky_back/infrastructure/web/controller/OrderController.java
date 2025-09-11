package com.darkhusky.darkhusky_back.infrastructure.web.controller;

import com.darkhusky.darkhusky_back.domain.model.Order;
import com.darkhusky.darkhusky_back.domain.model.ShippingAddress;
import com.darkhusky.darkhusky_back.domain.port.in.OrderUseCase;
import com.darkhusky.darkhusky_back.infrastructure.web.dto.CreateOrderRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderUseCase orderUseCase;

    public OrderController(OrderUseCase orderUseCase) {
        this.orderUseCase = orderUseCase;
    }

    @PostMapping
    public ResponseEntity<?> createOrder(
            @RequestHeader("X-User-Id") Long userId,
            @RequestBody @Valid CreateOrderRequest request) {
        try {
            ShippingAddress shippingAddress = new ShippingAddress(
                    null,
                    null,
                    request.getRecipientName(),
                    request.getAddress(),
                    request.getCity(),
                    request.getPostalCode(),
                    request.getCountry()
            );

            Order createdOrder = orderUseCase.createOrderFromCart(userId, shippingAddress);
            return ResponseEntity.ok(createdOrder);

        } catch (IllegalStateException e) {
            return ResponseEntity
                    .badRequest()
                    .body("Error al crear la orden: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<Order>> getOrderHistory(@RequestHeader("X-User-Id") Long userId) {
        List<Order> orders = orderUseCase.getOrderHistoryByUserId(userId);
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long id) {
        return orderUseCase.getOrderById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<Order> updateOrderStatus(
            @PathVariable Long id,
            @RequestBody StatusUpdateRequest request) {
        return orderUseCase.updateOrderStatus(id, request.getStatus())
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Simple DTO for status update
    static class StatusUpdateRequest {
        private String status;

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }
}
