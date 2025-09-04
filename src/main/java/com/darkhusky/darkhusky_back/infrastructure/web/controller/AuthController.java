package com.darkhusky.darkhusky_back.infrastructure.web.controller;

import com.darkhusky.darkhusky_back.domain.model.Usuario;
import com.darkhusky.darkhusky_back.domain.port.in.UsuarioUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Autenticación", description = "Operaciones para registro y login de usuarios")
public class AuthController {

    private final UsuarioUseCase usuarioUseCase;

    @Autowired
    public AuthController(UsuarioUseCase usuarioUseCase) {
        this.usuarioUseCase = usuarioUseCase;
    }

    @Operation(summary = "Registrar usuario", description = "Crea un usuario en el sistema")
    @ApiResponse(responseCode = "200", description = "Usuario creado",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Usuario.class)))
    @PostMapping("/register")
    public ResponseEntity<Usuario> register(@RequestBody Usuario user) {
        return ResponseEntity.ok(usuarioUseCase.registerUser(user));
    }

    @Operation(summary = "Iniciar sesión", description = "Autentica un usuario con email y contraseña")
    @ApiResponse(responseCode = "200",
            description = "Inicio de sesión exitoso",
            content = @Content(mediaType = "application/json")
    )
    @ApiResponse(
            responseCode = "401",
            description = "Credenciales inválidas"
    )
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Usuario loginRequest) {
        return usuarioUseCase.login(loginRequest.getEmail(), loginRequest.getPasswordHash())
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(401).build());
    }
}