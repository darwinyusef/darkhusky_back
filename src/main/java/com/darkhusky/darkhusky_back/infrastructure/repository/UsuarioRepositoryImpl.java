package com.darkhusky.darkhusky_back.infrastructure.repository;

import com.darkhusky.darkhusky_back.domain.model.Usuario;
import com.darkhusky.darkhusky_back.domain.repository.UsuarioRepository;
import com.darkhusky.darkhusky_back.infrastructure.persistence.entity.UsuarioEntity;
import com.darkhusky.darkhusky_back.infrastructure.repository.data.SpringDataUsuarioRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class UsuarioRepositoryImpl implements UsuarioRepository {

    private final SpringDataUsuarioRepository springDataRepository;

    public UsuarioRepositoryImpl(SpringDataUsuarioRepository springDataRepository) {
        this.springDataRepository = springDataRepository;
    }

    @Override
    public List<Usuario> findAll() {
        return springDataRepository.findAll()
                .stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Usuario> findById(Long id) {
        return springDataRepository.findById(id)
                .map(this::toDomain);
    }

    @Override
    public Usuario save(Usuario usuario) {
        UsuarioEntity entity = toEntity(usuario);
        UsuarioEntity savedEntity = springDataRepository.save(entity);
        return toDomain(savedEntity);
    }

    @Override
    public void delete(Usuario usuario) {
        UsuarioEntity entity = toEntity(usuario);
        springDataRepository.delete(entity);
    }

    // ==== Métodos de conversión ====
    private Usuario toDomain(UsuarioEntity entity) {
        return new Usuario(entity.getId(), entity.getUsername(), entity.getEmail(), null, null, null, null, null, null, null, null, null, null);
    }

    private UsuarioEntity toEntity(Usuario usuario) {
        return new UsuarioEntity(usuario.getId(), usuario.getUsername(), usuario.getEmail(), null, null, null, null, null, null, null, null, null, null);
    }
}
