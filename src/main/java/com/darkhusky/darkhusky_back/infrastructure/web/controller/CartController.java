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

    // A real implementation would get userId from the security context
    private static final Long MOCK_USER_ID = 1L;

    @GetMapping
    public ResponseEntity<List<CartItem>> getCartItems() {
        return cartUseCase.getCartByUserId(MOCK_USER_ID)
                .map(cart -> ResponseEntity.ok(cartUseCase.getCartItems(cart.getId())))
                .orElse(ResponseEntity.ok(List.of()));
    }

    @PostMapping("/items")
    public ResponseEntity<Void> addProductToCart(@RequestBody CartItemRequest request) {
        cartUseCase.addProductToCart(MOCK_USER_ID, request.getProductId(), request.getQuantity());
        return ResponseEntity.ok().build();
    }

    @PutMapping("/items/{productId}")
    public ResponseEntity<Void> updateCartItemQuantity(@PathVariable Long productId, @RequestBody CartItemRequest request) {
        cartUseCase.updateProductQuantityInCart(MOCK_USER_ID, productId, request.getQuantity());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/items/{productId}")
    public ResponseEntity<Void> removeProductFromCart(@PathVariable Long productId) {
        cartUseCase.removeProductFromCart(MOCK_USER_ID, productId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> clearCart() {
        cartUseCase.clearCart(MOCK_USER_ID);
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
