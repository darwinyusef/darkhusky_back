package com.darkhusky.darkhusky_back.domain.port.out;

import com.darkhusky.darkhusky_back.domain.model.CartSession;

import java.util.Optional;

public interface CartSessionRepositoryPort {
    CartSession save(CartSession cartSession);
    Optional<CartSession> findById(Long id);
    Optional<CartSession> findByUserId(Long userId);
    Optional<CartSession> findBySessionToken(String sessionToken);
    void deleteById(Long id);
}
