package com.darkhusky.darkhusky_back.infrastructure.config;

import com.darkhusky.darkhusky_back.application.service.UsuarioService;
import com.darkhusky.darkhusky_back.domain.port.in.UsuarioUseCase;
import com.darkhusky.darkhusky_back.domain.port.out.UsuarioRepositoryPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {

    @Bean
    public UsuarioUseCase usuarioUseCase(UsuarioRepositoryPort usuarioRepositoryPort) {
        return new UsuarioService(usuarioRepositoryPort);
    }
}
