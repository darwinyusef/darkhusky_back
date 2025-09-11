package com.darkhusky.darkhusky_back.infrastructure.web.controller;

import com.darkhusky.darkhusky_back.domain.model.QrCode;
import com.darkhusky.darkhusky_back.domain.port.in.QrCodeUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/qrcodes")
public class QrCodeController {

    private final QrCodeUseCase generateQrUseCase;

    public QrCodeController(QrCodeUseCase generateQrUseCase) {
        this.generateQrUseCase = generateQrUseCase;
    }

    @PostMapping("/generate")
    public ResponseEntity<String> generateCodes(@RequestParam(defaultValue = "10000") int quantity) {
        generateQrUseCase.generateCodes(quantity);
        return ResponseEntity.ok(quantity + " QRs generados correctamente.");
    }

    @PostMapping("/assign")
    public ResponseEntity<QrCode> assignQr(
            @RequestParam String relatedEntityType,
            @RequestParam Long relatedEntityId,
            @RequestParam(defaultValue = "PRODUCT_DETAILS") QrCode.QrType type
    ) {
        QrCode qr = generateQrUseCase.assignQr(relatedEntityType, relatedEntityId, type);
        return ResponseEntity.ok(qr);
    }

    @GetMapping("/check")
    public ResponseEntity<String> checkAvailability() {
        return ResponseEntity.ok("Sistema de QR operativo 🚀");
    }
}
