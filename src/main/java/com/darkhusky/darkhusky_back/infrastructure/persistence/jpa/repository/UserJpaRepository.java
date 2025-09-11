package com.darkhusky.darkhusky_back.infrastructure.persistence.jpa.repository;

import com.darkhusky.darkhusky_back.infrastructure.persistence.jpa.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserJpaRepository extends JpaRepository <UserEntity,Long> {
    Optional<UserEntity> findByEmail(String email);
    Optional<UserEntity> findById(Long id);
    Optional<UserEntity> findByUsername(String username);
}
