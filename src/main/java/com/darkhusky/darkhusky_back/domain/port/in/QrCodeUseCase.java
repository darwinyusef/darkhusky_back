package com.darkhusky.darkhusky_back.domain.port.in;

import com.darkhusky.darkhusky_back.domain.model.QrCode;

public interface QrCodeUseCase {
    void generateCodes(int quantity);
    QrCode assignQr(String relatedEntityType, Long relatedEntityId, QrCode.QrType type);
}
