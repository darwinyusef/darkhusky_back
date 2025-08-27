package com.darkhusky.darkhusky_back.infrastructure.web.controller;

import com.darkhusky.darkhusky_back.domain.model.Usuario;
import com.darkhusky.darkhusky_back.domain.port.in.UsuarioUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UsuarioUseCase usuarioUseCase;

    @Autowired
    public AuthController(UsuarioUseCase usuarioUseCase) {
        this.usuarioUseCase = usuarioUseCase;
    }

    @PostMapping("/register")
    public ResponseEntity<Usuario> register(@RequestBody Usuario user) {
        return ResponseEntity.ok(usuarioUseCase.registerUser(user));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Usuario loginRequest) {
        return usuarioUseCase.login(loginRequest.getEmail(), loginRequest.getPasswordHash())
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(401).build());
    }
}