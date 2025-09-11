package com.darkhusky.darkhusky_back.infrastructure.persistence.jpa.adapter;

import com.darkhusky.darkhusky_back.domain.model.CartSession;
import com.darkhusky.darkhusky_back.domain.port.out.CartSessionRepositoryPort;
import com.darkhusky.darkhusky_back.infrastructure.persistence.jpa.entity.CartSessionEntity;
import com.darkhusky.darkhusky_back.infrastructure.persistence.jpa.mapper.CartSessionMapper;
import com.darkhusky.darkhusky_back.infrastructure.persistence.jpa.repository.CartSessionJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CartSessionRepositoryAdapter implements CartSessionRepositoryPort {

    private final CartSessionJpaRepository cartSessionJpaRepository;

    public CartSessionRepositoryAdapter(CartSessionJpaRepository cartSessionJpaRepository) {
        this.cartSessionJpaRepository = cartSessionJpaRepository;
    }

    @Override
    public CartSession save(CartSession cartSession) {
        CartSessionEntity cartSessionEntity = CartSessionMapper.toEntity(cartSession);
        CartSessionEntity savedEntity = cartSessionJpaRepository.save(cartSessionEntity);
        return CartSessionMapper.toDomain(savedEntity);
    }

    @Override
    public Optional<CartSession> findById(Long id) {
        return cartSessionJpaRepository.findById(id).map(CartSessionMapper::toDomain);
    }

    @Override
    public Optional<CartSession> findByUserId(Long userId) {
        return cartSessionJpaRepository.findByUserUserId(userId).map(CartSessionMapper::toDomain);
    }

    @Override
    public Optional<CartSession> findBySessionToken(String sessionToken) {
        return cartSessionJpaRepository.findBySessionToken(sessionToken).map(CartSessionMapper::toDomain);
    }

    @Override
    public void deleteById(Long id) {
        cartSessionJpaRepository.deleteById(id);
    }
}
