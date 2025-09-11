package com.darkhusky.darkhusky_back.infrastructure.config;

import com.darkhusky.darkhusky_back.application.service.UsuarioService;
import com.darkhusky.darkhusky_back.domain.port.in.UsuarioUseCase;
import com.darkhusky.darkhusky_back.domain.port.out.UsuarioRepositoryPort;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {

    @Bean
    public UsuarioUseCase usuarioUseCase(UsuarioRepositoryPort usuarioRepositoryPort) {
        return new UsuarioService(usuarioRepositoryPort);
    }
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Darkhusky API")
                        .version("0.0.1")
                        .description("Documentación OpenAPI generada por springdoc"));
    }
}
