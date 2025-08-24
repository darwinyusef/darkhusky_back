package com.darkhusky.darkhusky_back.application.service;

import com.darkhusky.darkhusky_back.domain.model.Usuario;
import com.darkhusky.darkhusky_back.domain.repository.UsuarioRepository;
import com.darkhusky.darkhusky_back.infrastructure.persistence.entity.UsuarioEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    // ====================
    // Métodos Públicos
    // ====================

    public List<Usuario> findAll() {
        return usuarioRepository.findAll()
                .stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    public Optional<Usuario> findById(Long id) {
        return usuarioRepository.findById(id).map(this::toDomain);
    }

    public Optional<Usuario> update(Long id, Usuario newUser) {
        return usuarioRepository.findById(id)
                .map(entity -> {
                    entity.setEmail(newUser.getEmail());
                    entity.setPasswordHash(newUser.getPasswordHash());
                    return usuarioRepository.save(entity);
                })
                .map(this::toDomain);
    }

    public boolean delete(Long id) {
        return usuarioRepository.findById(id)
                .map(entity -> {
                    usuarioRepository.delete(entity);
                    return true;
                })
                .orElse(false);
    }

    // ====================
    // Métodos de Conversión
    // ====================

    private Usuario toDomain(UsuarioEntity entity) {
        return new Usuario(entity.getId(), entity.getEmail(), entity.getPasswordHash());
    }

    private UsuarioEntity toEntity(Usuario usuario) {
        UsuarioEntity entity = new UsuarioEntity();
        entity.setId(usuario.getId());
        entity.setEmail(usuario.getEmail());
        entity.setPasswordHash(usuario.getPasswordHash());
        return entity;
    }
}
