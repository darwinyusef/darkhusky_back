package com.darkhusky.darkhusky_back.controller;

import com.darkhusky.darkhusky_back.entity.QrCodeEntity;
import com.darkhusky.darkhusky_back.service.QrCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/qrcodes")
public class QrCodeController {

    private final QrCodeService qrCodeService;

    @Autowired
    public QrCodeController(QrCodeService qrCodeService) {
        this.qrCodeService = qrCodeService;
    }

    @PostMapping("/generate")
    public ResponseEntity<String> generateCodes(@RequestParam(defaultValue = "10000") int quantity) {
        qrCodeService.generateCodes(quantity);
        return ResponseEntity.ok(quantity + " QRs generados correctamente.");
    }

    @PostMapping("/assign")
    public ResponseEntity<QrCodeEntity> assignQr(
            @RequestParam String relatedEntityType,
            @RequestParam Long relatedEntityId,
            @RequestParam(defaultValue = "PRODUCT_DETAILS") QrCodeEntity.QrType type
    ) {
        QrCodeEntity qr = qrCodeService.assignQr(relatedEntityType, relatedEntityId, type);
        return ResponseEntity.ok(qr);
    }

    @GetMapping("/check")
    public ResponseEntity<String> checkAvailability() {
        return ResponseEntity.ok("Sistema de QR operativo 🚀");
    }
}
