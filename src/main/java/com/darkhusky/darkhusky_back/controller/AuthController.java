package com.darkhusky.darkhusky_back.controller;

import com.darkhusky.darkhusky_back.entity.UsuarioEntity;
import com.darkhusky.darkhusky_back.service.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UsuarioService userService;

    public AuthController(UsuarioService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<UsuarioEntity> register(@RequestBody UsuarioEntity user) {
        return ResponseEntity.ok(userService.registro(user));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UsuarioEntity loginRequest) {
        return userService.login(loginRequest.getEmail(), loginRequest.getPasswordHash())
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(401).build());
    }
}