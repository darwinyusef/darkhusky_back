package com.darkhusky.darkhusky_back.application.service;

import com.darkhusky.darkhusky_back.domain.model.Usuario;
import com.darkhusky.darkhusky_back.domain.port.in.UsuarioUseCase;
import com.darkhusky.darkhusky_back.domain.port.out.UsuarioRepositoryPort;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class UsuarioService implements UsuarioUseCase {

    private final UsuarioRepositoryPort usuarioRepositoryPort;

    public UsuarioService(UsuarioRepositoryPort usuarioRepositoryPort) {
        this.usuarioRepositoryPort = usuarioRepositoryPort;
    }

    @Override
    public Usuario registerUser(Usuario usuario) {
        usuario.setIsActive(true);
        usuario.setCreatedAt(LocalDateTime.now());
        usuario.setUpdatedAt(LocalDateTime.now());
        return usuarioRepositoryPort.save(usuario);
    }

    @Override
    public Optional<Usuario> login(String email, String password) {
        return usuarioRepositoryPort.findByEmail(email)
                .filter(u -> u.getPasswordHash().equals(password));
    }

    @Override
    public List<Usuario> getAllUsers() {
        return usuarioRepositoryPort.findAll();
    }

    @Override
    public Optional<Usuario> getUserById(Long id) {
        return usuarioRepositoryPort.findById(id);
    }

    @Override
    public Optional<Usuario> updateUser(Long id, Usuario usuario) {
        return usuarioRepositoryPort.findById(id)
                .map(existingUser -> {
                    existingUser.setUsername(usuario.getUsername());
                    existingUser.setEmail(usuario.getEmail());
                    existingUser.setPasswordHash(usuario.getPasswordHash());
                    existingUser.setFullName(usuario.getFullName());
                    existingUser.setPhone(usuario.getPhone());
                    existingUser.setAddress(usuario.getAddress());
                    existingUser.setEmergencyContact(usuario.getEmergencyContact());
                    existingUser.setEmergencyPhone(usuario.getEmergencyPhone());
                    existingUser.setUpdatedAt(LocalDateTime.now());
                    return usuarioRepositoryPort.save(existingUser);
                });
    }

    @Override
    public boolean deleteUser(Long id) {
        return usuarioRepositoryPort.findById(id)
                .map(user -> {
                    usuarioRepositoryPort.deleteById(id);
                    return true;
                })
                .orElse(false);
    }
}
