package com.darkhusky.darkhusky_back.entity.repository;

import com.darkhusky.darkhusky_back.entity.QrCodeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface QrCodeRepository extends JpaRepository<QrCodeEntity, Long> {

    Optional<QrCodeEntity> findFirstByStatusOrderByIdAsc(QrCodeEntity.QrStatus status);
}
