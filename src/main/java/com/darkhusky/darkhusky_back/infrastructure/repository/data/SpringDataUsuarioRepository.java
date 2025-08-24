package com.darkhusky.darkhusky_back.infrastructure.repository.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.darkhusky.darkhusky_back.infrastructure.persistence.entity.UsuarioEntity;

@Repository
public interface SpringDataUsuarioRepository extends JpaRepository<UsuarioEntity, Long> {
}