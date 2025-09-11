package com.darkhusky.darkhusky_back.application.service;

import com.darkhusky.darkhusky_back.domain.model.CartItem;
import com.darkhusky.darkhusky_back.domain.model.CartSession;
import com.darkhusky.darkhusky_back.domain.model.Product;
import com.darkhusky.darkhusky_back.domain.port.in.CartUseCase;
import com.darkhusky.darkhusky_back.domain.port.out.CartItemRepositoryPort;
import com.darkhusky.darkhusky_back.domain.port.out.CartSessionRepositoryPort;
import com.darkhusky.darkhusky_back.domain.port.out.ProductRepositoryPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CartService implements CartUseCase {

    private final CartSessionRepositoryPort cartSessionRepositoryPort;
    private final CartItemRepositoryPort cartItemRepositoryPort;
    private final ProductRepositoryPort productRepositoryPort;

    public CartService(CartSessionRepositoryPort cartSessionRepositoryPort, CartItemRepositoryPort cartItemRepositoryPort, ProductRepositoryPort productRepositoryPort) {
        this.cartSessionRepositoryPort = cartSessionRepositoryPort;
        this.cartItemRepositoryPort = cartItemRepositoryPort;
        this.productRepositoryPort = productRepositoryPort;
    }

    @Override
    @Transactional
    public void addProductToCart(Long userId, Long productId, int quantity) {
        CartSession cart = getOrCreateCart(userId);
        Product product = productRepositoryPort.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        Optional<CartItem> existingItem = cartItemRepositoryPort.findByCartIdAndProductId(cart.getId(), productId);

        if (existingItem.isPresent()) {
            CartItem item = existingItem.get();
            item.setQuantity(item.getQuantity() + quantity);
            cartItemRepositoryPort.save(item);
        } else {
            CartItem newItem = new CartItem(null, cart.getId(), productId, quantity, product.getPrice());
            cartItemRepositoryPort.save(newItem);
        }
    }

    @Override
    @Transactional
    public void removeProductFromCart(Long userId, Long productId) {
        getCartByUserId(userId).ifPresent(cart -> {
            cartItemRepositoryPort.findByCartIdAndProductId(cart.getId(), productId).ifPresent(item -> {
                cartItemRepositoryPort.deleteById(item.getId());
            });
        });
    }

    @Override
    @Transactional
    public void updateProductQuantityInCart(Long userId, Long productId, int quantity) {
        getCartByUserId(userId).ifPresent(cart -> {
            cartItemRepositoryPort.findByCartIdAndProductId(cart.getId(), productId).ifPresent(item -> {
                if (quantity > 0) {
                    item.setQuantity(quantity);
                    cartItemRepositoryPort.save(item);
                } else {
                    cartItemRepositoryPort.deleteById(item.getId());
                }
            });
        });
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CartSession> getCartByUserId(Long userId) {
        return cartSessionRepositoryPort.findByUserId(userId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CartItem> getCartItems(Long cartId) {
        return cartItemRepositoryPort.findByCartId(cartId);
    }

    @Override
    @Transactional
    public void clearCart(Long userId) {
        getCartByUserId(userId).ifPresent(cart -> {
            cartItemRepositoryPort.deleteByCartId(cart.getId());
        });
    }

    private CartSession getOrCreateCart(Long userId) {
        return cartSessionRepositoryPort.findByUserId(userId).orElseGet(() -> {
            CartSession newCart = new CartSession();
            newCart.setUserId(userId);
            newCart.setSessionToken(UUID.randomUUID().toString());
            newCart.setCreatedAt(LocalDateTime.now());
            return cartSessionRepositoryPort.save(newCart);
        });
    }
}
