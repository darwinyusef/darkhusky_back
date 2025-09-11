package com.darkhusky.darkhusky_back.infrastructure.persistence.jpa.repository;

import com.darkhusky.darkhusky_back.domain.model.QrCode;
import com.darkhusky.darkhusky_back.infrastructure.persistence.jpa.entity.QrCodeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaQrCodeRepository extends JpaRepository<QrCodeEntity, Long> {
    Optional<QrCodeEntity> findFirstByStatusOrderByIdAsc(QrCode.QrStatus status);
}

