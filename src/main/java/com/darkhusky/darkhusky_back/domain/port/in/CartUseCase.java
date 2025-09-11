package com.darkhusky.darkhusky_back.domain.port.in;

import com.darkhusky.darkhusky_back.domain.model.CartItem;
import com.darkhusky.darkhusky_back.domain.model.CartSession;

import java.util.List;
import java.util.Optional;

public interface CartUseCase {
    // "Agregar productos al carrito"
    void addProductToCart(Long userId, Long productId, int quantity);

    void removeProductFromCart(Long userId, Long productId);

    void updateProductQuantityInCart(Long userId, Long productId, int quantity);

    Optional<CartSession> getCartByUserId(Long userId);

    List<CartItem> getCartItems(Long cartId);

    void clearCart(Long userId);
}
