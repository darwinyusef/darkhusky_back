package com.darkhusky.darkhusky_back.infrastructure.persistence.jpa.adapter;

import com.darkhusky.darkhusky_back.domain.model.QrCode;

import com.darkhusky.darkhusky_back.domain.port.out.QrCodeRepositoryPort;
import com.darkhusky.darkhusky_back.infrastructure.persistence.jpa.entity.QrCodeEntity;
import com.darkhusky.darkhusky_back.infrastructure.persistence.jpa.mapper.QrCodeMapper;
import com.darkhusky.darkhusky_back.infrastructure.persistence.jpa.repository.JpaQrCodeRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class QrCodeRepositoryAdapter implements QrCodeRepositoryPort {

    private final JpaQrCodeRepository jpaRepository;

    public QrCodeRepositoryAdapter(JpaQrCodeRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public void save(QrCode qr) {
        QrCodeEntity entity = QrCodeMapper.toEntity(qr);
        QrCodeEntity saved = jpaRepository.save(entity);
        qr.setId(saved.getId());
        qr.setCreatedAt(saved.getCreatedAt());
        qr.setUpdatedAt(saved.getUpdatedAt());
    }

    @Override
    public Optional<QrCode> findFirstInactive() {
        return jpaRepository.findFirstByStatusOrderByIdAsc(QrCode.QrStatus.INACTIVE)
                .map(QrCodeMapper::toDomain);
    }

}
