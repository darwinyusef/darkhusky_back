package com.darkhusky.darkhusky_back.infrastructure.persistence.jpa.adapter;

import com.darkhusky.darkhusky_back.domain.model.Usuario;
import com.darkhusky.darkhusky_back.domain.port.out.UsuarioRepositoryPort;
import com.darkhusky.darkhusky_back.infrastructure.persistence.jpa.entity.UserEntity;
import com.darkhusky.darkhusky_back.infrastructure.persistence.jpa.repository.UserJpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class UserRepositoryAdapter implements UsuarioRepositoryPort {

    private final UserJpaRepository userJpaRepository;

    public UserRepositoryAdapter(UserJpaRepository userJpaRepository) {
        this.userJpaRepository = userJpaRepository;
    }

    @Override
    public Usuario save(Usuario usuario) {
        UserEntity userEntity = toEntity(usuario);
        UserEntity savedEntity = userJpaRepository.save(userEntity);
        return toDomainModel(savedEntity);
    }

    @Override
    public Optional<Usuario> findByEmail(String email) {
        return userJpaRepository.findByEmail(email).map(this::toDomainModel);
    }

    @Override
    public Optional<Usuario> findById(Long id) {
        return userJpaRepository.findById(id).map(this::toDomainModel);
    }

    @Override
    public List<Usuario> findAll() {
        return userJpaRepository.findAll().stream()
                .map(this::toDomainModel)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        userJpaRepository.deleteById(id);
    }


    private UserEntity toEntity(Usuario usuario) {
        UserEntity entity = new UserEntity();
        entity.setUserId(usuario.getUserId());
        entity.setUsername(usuario.getUsername());
        entity.setEmail(usuario.getEmail());
        entity.setPasswordHash(usuario.getPasswordHash());
        entity.setIsActive(usuario.getIsActive());
        entity.setRole(usuario.getRole());
        entity.setFullName(usuario.getFullName());
        entity.setPhone(usuario.getPhone());
        entity.setAddress(usuario.getAddress());
        entity.setEmergencyContact(usuario.getEmergencyContact());
        entity.setEmergencyPhone(usuario.getEmergencyPhone());
        entity.setCreatedAt(usuario.getCreatedAt());
        entity.setUpdatedAt(usuario.getUpdatedAt());
        return entity;
    }

    private Usuario toDomainModel(UserEntity entity) {
        return new Usuario(
                entity.getUserId(),
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
}
