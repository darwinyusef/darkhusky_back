package com.darkhusky.darkhusky_back.domain.repository;

import java.util.List;
import java.util.Optional;

import com.darkhusky.darkhusky_back.domain.model.Usuario;

public interface UsuarioRepository {
    Optional<Usuario> findById(Long id);
    List<Usuario> findAll();
    Usuario save(Usuario usuario);
    void delete(Usuario usuario);
}