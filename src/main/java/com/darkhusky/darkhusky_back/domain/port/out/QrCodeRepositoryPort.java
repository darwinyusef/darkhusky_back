package com.darkhusky.darkhusky_back.domain.port.out;

import com.darkhusky.darkhusky_back.domain.model.QrCode;

import java.util.Optional;

public interface QrCodeRepositoryPort {
    void save(QrCode qr);
    Optional<QrCode> findFirstInactive();
}
