package com.darkhusky.darkhusky_back.infrastructure.mapper;

import com.darkhusky.darkhusky_back.domain.model.Usuario;
import com.darkhusky.darkhusky_back.infrastructure.persistence.entity.UsuarioEntity;

public class UsuarioMapper {

    public static Usuario toDomain(UsuarioEntity entity) {
        return new Usuario(
            entity.getId(),
            entity.getUsername(),
            entity.getEmail(),
            entity.getPasswordHash(),
            entity.getIsActive(),
            entity.getRole(),
            entity.getFullName(),
            entity.getPhone(),
            entity.getAddress(),
            entity.getEmergencyContact(),
            entity.getEmergencyPhone(),
            entity.getCreatedAt(),
            entity.getUpdatedAt()
        );
    }

    public static UsuarioEntity toEntity(Usuario domain) {
        UsuarioEntity entity = new UsuarioEntity();
        entity.setId(domain.getId());
        entity.setUsername(domain.getUsername());
        entity.setEmail(domain.getEmail());
        entity.setPasswordHash(domain.getPasswordHash());
        entity.setIsActive(domain.getIsActive());
        entity.setRole(domain.getRole());
        entity.setFullName(domain.getFullName());
        entity.setPhone(domain.getPhone());
        entity.setAddress(domain.getAddress());
        entity.setEmergencyContact(domain.getEmergencyContact());
        entity.setEmergencyPhone(domain.getEmergencyPhone());
        entity.setCreatedAt(domain.getCreatedAt());
        entity.setUpdatedAt(domain.getUpdatedAt());
        return entity;
    }
}