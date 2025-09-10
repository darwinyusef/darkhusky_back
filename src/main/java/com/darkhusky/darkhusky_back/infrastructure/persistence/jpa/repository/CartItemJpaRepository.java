package com.darkhusky.darkhusky_back.infrastructure.persistence.jpa.repository;

import com.darkhusky.darkhusky_back.infrastructure.persistence.jpa.entity.CartItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CartItemJpaRepository extends JpaRepository<CartItemEntity, Long> {
    List<CartItemEntity> findByCartSessionId(Long cartId);
    Optional<CartItemEntity> findByCartSessionIdAndProductId(Long cartId, Long productId);

    @Modifying
    @Query("DELETE FROM CartItemEntity c WHERE c.cartSession.id = :cartId")
    void deleteByCartSessionId(Long cartId);
}
