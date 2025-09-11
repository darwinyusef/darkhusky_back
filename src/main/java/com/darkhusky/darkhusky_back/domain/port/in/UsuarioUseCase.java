package com.darkhusky.darkhusky_back.domain.port.in;

import com.darkhusky.darkhusky_back.domain.model.Usuario;

import java.util.List;
import java.util.Optional;

public interface UsuarioUseCase {
    Usuario registerUser(Usuario usuario);
    Optional<Usuario> login(String email, String password);
    List<Usuario> getAllUsers();
    Optional<Usuario> getUserById(Long id);
    Optional<Usuario> updateUser(Long id, Usuario usuario);
    boolean deleteUser(Long id);
}
