package com.darkhusky.darkhusky_back.infrastructure.persistence.jpa.repository;

import com.darkhusky.darkhusky_back.infrastructure.persistence.jpa.entity.CartSessionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartSessionJpaRepository extends JpaRepository<CartSessionEntity, Long> {
    Optional<CartSessionEntity> findByUserUserId(Long userId);
    Optional<CartSessionEntity> findBySessionToken(String sessionToken);
}
