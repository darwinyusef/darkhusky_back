package com.darkhusky.darkhusky_back.infrastructure.persistence.jpa.adapter;

import com.darkhusky.darkhusky_back.domain.model.CartItem;
import com.darkhusky.darkhusky_back.domain.port.out.CartItemRepositoryPort;
import com.darkhusky.darkhusky_back.infrastructure.persistence.jpa.entity.CartItemEntity;
import com.darkhusky.darkhusky_back.infrastructure.persistence.jpa.mapper.CartItemMapper;
import com.darkhusky.darkhusky_back.infrastructure.persistence.jpa.repository.CartItemJpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class CartItemRepositoryAdapter implements CartItemRepositoryPort {

    private final CartItemJpaRepository cartItemJpaRepository;

    public CartItemRepositoryAdapter(CartItemJpaRepository cartItemJpaRepository) {
        this.cartItemJpaRepository = cartItemJpaRepository;
    }

    @Override
    public CartItem save(CartItem cartItem) {
        CartItemEntity cartItemEntity = CartItemMapper.toEntity(cartItem);
        CartItemEntity savedEntity = cartItemJpaRepository.save(cartItemEntity);
        return CartItemMapper.toDomain(savedEntity);
    }

    @Override
    public Optional<CartItem> findById(Long id) {
        return cartItemJpaRepository.findById(id).map(CartItemMapper::toDomain);
    }

    @Override
    public List<CartItem> findByCartId(Long cartId) {
        return cartItemJpaRepository.findByCartSessionId(cartId).stream()
                .map(CartItemMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<CartItem> findByCartIdAndProductId(Long cartId, Long productId) {
        return cartItemJpaRepository.findByCartSessionIdAndProductId(cartId, productId)
                .map(CartItemMapper::toDomain);
    }

    @Override
    public void deleteById(Long id) {
        cartItemJpaRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void deleteByCartId(Long cartId) {
        cartItemJpaRepository.deleteByCartSessionId(cartId);
    }
}
