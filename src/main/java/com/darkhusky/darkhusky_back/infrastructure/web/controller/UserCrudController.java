package com.darkhusky.darkhusky_back.infrastructure.web.controller;

import com.darkhusky.darkhusky_back.domain.model.Usuario;
import com.darkhusky.darkhusky_back.domain.port.in.UsuarioUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserCrudController {

    private final UsuarioUseCase usuarioUseCase;

    @Autowired
    public UserCrudController(UsuarioUseCase usuarioUseCase) {
        this.usuarioUseCase = usuarioUseCase;
    }

    @GetMapping
    public ResponseEntity<List<Usuario>> getAllUsers() {
        return ResponseEntity.ok(usuarioUseCase.getAllUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        Optional<Usuario> usuario = usuarioUseCase.getUserById(id);
        if (usuario.isPresent()) {
            return ResponseEntity.ok(usuario.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody Usuario usuario) {
        Optional<Usuario> updatedUsuario = usuarioUseCase.updateUser(id, usuario);
        if (updatedUsuario.isPresent()) {
            return ResponseEntity.ok(updatedUsuario.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        if (usuarioUseCase.deleteUser(id)) {
            return ResponseEntity.ok("Usuario eliminado");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
