package com.darkhusky.darkhusky_back.application.service;

import com.darkhusky.darkhusky_back.domain.model.QrCode;
import com.darkhusky.darkhusky_back.domain.port.in.QrCodeUseCase;
import com.darkhusky.darkhusky_back.domain.port.out.QrCodeRepositoryPort;
import com.darkhusky.darkhusky_back.domain.port.out.QrImageGeneratorPort;
import com.darkhusky.darkhusky_back.domain.port.out.UrlShortenerPort;

import java.util.NoSuchElementException;

public class QrCodeService implements QrCodeUseCase {

    private final QrCodeRepositoryPort qrCodeRepository;
    private final QrImageGeneratorPort qrImageGenerator;
    private final UrlShortenerPort urlShortener;
    private final CodeIterator codeIterator = new CodeIterator();
    private static final int BATCH_SIZE = 10_000;

    public QrCodeService(QrCodeRepositoryPort qrCodeRepository, QrImageGeneratorPort qrImageGenerator, UrlShortenerPort urlShortener) {
        this.qrCodeRepository = qrCodeRepository;
        this.qrImageGenerator = qrImageGenerator;
        this.urlShortener = urlShortener;
    }

    @Override
    public void generateCodes(int quantity) {
        for (int i = 0; i < quantity; i++) {
            String code = codeIterator.next();
            String longUrl = "https://darkhusky.com.co/qr/" + code;
            String shortUrl = urlShortener.shortenUrl(longUrl);
            qrImageGenerator.generateQrImage(code, shortUrl);

            QrCode qr = new QrCode();
            qr.setQrCode(code);
            qr.setRelatedEntityType("NONE");
            qr.setRelatedEntityId(0L);
            qr.setType(QrCode.QrType.PRODUCT_DETAILS);
            qr.setStatus(QrCode.QrStatus.INACTIVE);
            qrCodeRepository.save(qr);
        }
    }

    @Override
    public QrCode assignQr(String relatedEntityType, Long relatedEntityId, QrCode.QrType type) {
        QrCode qr = qrCodeRepository.findFirstInactive()
                .orElseGet(() -> {
                    generateCodes(BATCH_SIZE);
                    return qrCodeRepository.findFirstInactive()
                            .orElseThrow(() -> new NoSuchElementException("No se pudieron generar más QR."));
                });

        qr.setRelatedEntityType(relatedEntityType);
        qr.setRelatedEntityId(relatedEntityId);
        qr.setType(type);
        qr.setStatus(QrCode.QrStatus.ACTIVE);
        qrCodeRepository.save(qr);
        return qr;
    }

    private static class CodeIterator implements java.util.Iterator<String> {
        private int prefixIndex = 0;
        private int number = 1;

        @Override
        public boolean hasNext() { return true; }

        @Override
        public String next() {
            String prefix = "" + (char) ('A' + (prefixIndex / 26)) + (char) ('A' + (prefixIndex % 26));
            String code = prefix + String.format("%02d", number);

            number++;
            if (number > 99) {
                number = 1;
                prefixIndex++;
            }
            return code;
        }
    }
}
