package com.darkhusky.darkhusky_back.infrastructure.config;

import com.darkhusky.darkhusky_back.application.service.QrCodeService;
import com.darkhusky.darkhusky_back.domain.port.in.QrCodeUseCase;
import com.darkhusky.darkhusky_back.infrastructure.persistence.jpa.adapter.QrCodeImageGeneratorAdapter;
import com.darkhusky.darkhusky_back.infrastructure.persistence.jpa.adapter.QrCodeRepositoryAdapter;
import com.darkhusky.darkhusky_back.infrastructure.persistence.jpa.adapter.TinyUrlShortenerAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QrCodeConfig {

    @Bean
    public QrCodeUseCase generateQrUseCase(QrCodeRepositoryAdapter repoAdapter,
                                           QrCodeImageGeneratorAdapter imageGenerator,
                                           TinyUrlShortenerAdapter urlShortener) {
        return new QrCodeService(repoAdapter, imageGenerator, urlShortener);
    }

    @Bean
    public QrCodeUseCase assignQrUseCase(QrCodeRepositoryAdapter repoAdapter,
                                           QrCodeImageGeneratorAdapter imageGenerator,
                                           TinyUrlShortenerAdapter urlShortener) {
        return new QrCodeService(repoAdapter, imageGenerator, urlShortener);
    }
}
