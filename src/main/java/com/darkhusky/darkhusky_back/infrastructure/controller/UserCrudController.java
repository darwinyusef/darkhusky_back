package com.darkhusky.darkhusky_back.infrastructure.controller;

import com.darkhusky.darkhusky_back.application.service.UsuarioService;
import com.darkhusky.darkhusky_back.domain.repository.UsuarioRepository;
import com.darkhusky.darkhusky_back.infrastructure.persistence.entity.UsuarioEntity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserCrudController {
    private UsuarioRepository usuarioRepository;
    private UsuarioService usuarioService;

    @Autowired
    public UserCrudController(UsuarioService usuarioService,UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
        this.usuarioService = usuarioService;
    }

    @Value("${INFO}")
    private String algo;

    @GetMapping("/valor")
    public String getValor() {
        return "El valor de algo es: " + algo;
    }

    @GetMapping
    public ResponseEntity<List<UsuarioEntity>> getAllUsers() {
        return ResponseEntity.ok(usuarioService.getAllUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        return usuarioService.getUserById(id)
                .map(usuario -> ResponseEntity.ok(usuario))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado"));
    }

    // Actualizar usuario
    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody UsuarioEntity newUser) {
        return usuarioRepository.findById(id)
                .map(user -> {
                    user.setEmail(newUser.getEmail());
                    user.setPasswordHash(newUser.getPasswordHash());
                    return ResponseEntity.ok(usuarioRepository.save(user));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // Eliminar usuario
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        return usuarioRepository.findById(id)
                .map(user -> {
                    usuarioRepository.delete(user);
                    return ResponseEntity.ok("Usuario eliminado");
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
