package com.darkhusky.darkhusky_back.domain.port.out;

import com.darkhusky.darkhusky_back.domain.model.CartItem;

import java.util.List;
import java.util.Optional;

public interface CartItemRepositoryPort {
    CartItem save(CartItem cartItem);
    Optional<CartItem> findById(Long id);
    List<CartItem> findByCartId(Long cartId);
    Optional<CartItem> findByCartIdAndProductId(Long cartId, Long productId);
    void deleteById(Long id);
    void deleteByCartId(Long cartId);
}
