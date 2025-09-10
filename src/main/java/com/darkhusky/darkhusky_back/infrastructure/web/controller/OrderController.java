package com.darkhusky.darkhusky_back.infrastructure.web.controller;

import com.darkhusky.darkhusky_back.domain.model.Order;
import com.darkhusky.darkhusky_back.domain.model.ShippingAddress;
import com.darkhusky.darkhusky_back.domain.port.in.OrderUseCase;
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

    // A real implementation would get userId from the security context
    private static final Long MOCK_USER_ID = 1L;

    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody ShippingAddress shippingAddress) {
        try {
            Order createdOrder = orderUseCase.createOrderFromCart(MOCK_USER_ID, shippingAddress);
            return new ResponseEntity<>(createdOrder, HttpStatus.CREATED);
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<Order>> getOrderHistory() {
        List<Order> orders = orderUseCase.getOrderHistoryByUserId(MOCK_USER_ID);
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long id) {
        return orderUseCase.getOrderById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<Order> updateOrderStatus(@PathVariable Long id, @RequestBody StatusUpdateRequest request) {
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
