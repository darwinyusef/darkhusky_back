package com.darkhusky.darkhusky_back.infrastructure.web.controller;

import com.darkhusky.darkhusky_back.domain.model.CartItem;
import com.darkhusky.darkhusky_back.domain.model.CartSession;
import com.darkhusky.darkhusky_back.domain.port.in.CartUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    private final CartUseCase cartUseCase;

    public CartController(CartUseCase cartUseCase) {
        this.cartUseCase = cartUseCase;
    }

    @GetMapping
    public ResponseEntity<List<CartItem>> getCartItems(@RequestHeader("X-User-Id") Long userId) {
        return cartUseCase.getCartByUserId(userId)
                .map(cart -> ResponseEntity.ok(cartUseCase.getCartItems(cart.getId())))
                .orElse(ResponseEntity.ok(List.of()));
    }

    @PostMapping("/items")
    public ResponseEntity<Void> addProductToCart(
            @RequestHeader("X-User-Id") Long userId,
            @RequestBody CartItemRequest request) {
        cartUseCase.addProductToCart(userId, request.getProductId(), request.getQuantity());
        return ResponseEntity.ok().build();
    }

    @PutMapping("/items/{productId}")
    public ResponseEntity<Void> updateCartItemQuantity(
            @RequestHeader("X-User-Id") Long userId,
            @PathVariable Long productId,
            @RequestBody CartItemRequest request) {
        cartUseCase.updateProductQuantityInCart(userId, productId, request.getQuantity());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/items/{productId}")
    public ResponseEntity<Void> removeProductFromCart(
            @RequestHeader("X-User-Id") Long userId,
            @PathVariable Long productId) {
        cartUseCase.removeProductFromCart(userId, productId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> clearCart(@RequestHeader("X-User-Id") Long userId) {
        cartUseCase.clearCart(userId);
        return ResponseEntity.noContent().build();
    }

    // Simple DTO for cart requests
    static class CartItemRequest {
        private Long productId;
        private int quantity;

        public Long getProductId() {
            return productId;
        }

        public void setProductId(Long productId) {
            this.productId = productId;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }
    }
}
