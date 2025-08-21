package com.darkhusky.darkhusky_back.controller;

import com.darkhusky.darkhusky_back.entity.UsuarioEntity;
import com.darkhusky.darkhusky_back.entity.repository.UsuarioRepository;
import com.darkhusky.darkhusky_back.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping
    public ResponseEntity<List<UsuarioEntity>> getAllUsers() {
        return ResponseEntity.ok(usuarioRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        Optional<UsuarioEntity> user = usuarioRepository.findById(id);
        if (user.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(user.get());
        }else {
            return ResponseEntity.status(404).body("Usuario no encontrado");
        }
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
