package com.darkhusky.darkhusky_back.entity.repository;

import com.darkhusky.darkhusky_back.entity.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository <UsuarioEntity,Long> {
    Optional<UsuarioEntity> findByEmail(String email);
    Optional<UsuarioEntity> findById(Long id);
    Optional<UsuarioEntity> findByUsername(String username);
}
