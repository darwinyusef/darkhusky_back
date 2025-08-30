package com.darkhusky.darkhusky_back.service;

import com.darkhusky.darkhusky_back.entity.QrCodeEntity;
import com.darkhusky.darkhusky_back.entity.repository.QrCodeRepository;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.*;

@Service
public class QrCodeService {

    private final QrCodeRepository qrCodeRepository;
    private final CodeIterator codeIterator = new CodeIterator();
    private static final int BATCH_SIZE = 10_000;

    @Autowired
    public QrCodeService(QrCodeRepository qrCodeRepository) {
        this.qrCodeRepository = qrCodeRepository;
    }

    public void generateCodes(int quantity) {
        for (int i = 0; i < quantity; i++) {
            String code = codeIterator.next();
            String longUrl = "https://darkhusky.com.co/qr/" + code;

            // URL corta
            String shortUrl = shortenUrl(longUrl);

            try {
                generateQrImage(code, shortUrl);
            } catch (Exception e) {
                throw new RuntimeException("Error generando imagen QR: " + e.getMessage(), e);
            }

            QrCodeEntity qr = new QrCodeEntity();
            qr.setQrCode(code);
            qr.setRelatedEntityType("NONE");
            qr.setRelatedEntityId(0L);
            qr.setType(QrCodeEntity.QrType.PRODUCT_DETAILS);
            qr.setStatus(QrCodeEntity.QrStatus.INACTIVE);
            qrCodeRepository.save(qr);
        }
    }

    public QrCodeEntity assignQr(String relatedEntityType, Long relatedEntityId, QrCodeEntity.QrType type) {
        QrCodeEntity qr = qrCodeRepository.findFirstByStatusOrderByIdAsc(QrCodeEntity.QrStatus.INACTIVE)
                .orElseGet(() -> {
                    generateCodes(BATCH_SIZE);
                    return qrCodeRepository.findFirstByStatusOrderByIdAsc(QrCodeEntity.QrStatus.INACTIVE)
                            .orElseThrow(() -> new NoSuchElementException("No se pudieron generar más QR."));
                });

        qr.setRelatedEntityType(relatedEntityType);
        qr.setRelatedEntityId(relatedEntityId);
        qr.setType(type);
        qr.setStatus(QrCodeEntity.QrStatus.ACTIVE);

        return qrCodeRepository.save(qr);
    }

    private void generateQrImage(String code, String url) {
        try {
            String outputDir = "qrcodes";
            File directory = new File(outputDir);

            if (!directory.exists()) {
                directory.mkdirs();
            }

            String filePath = outputDir + File.separator + code + ".png";

            // Configuración QR
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            Map<EncodeHintType, Object> hints = new HashMap<>();
            hints.put(EncodeHintType.MARGIN, 0);

            int size = 177;

            BitMatrix bitMatrix = qrCodeWriter.encode(
                    url,
                    BarcodeFormat.QR_CODE,
                    size, size,
                    hints
            );

            Path path = FileSystems.getDefault().getPath(filePath);

            MatrixToImageConfig config = new MatrixToImageConfig(
                    MatrixToImageConfig.BLACK, MatrixToImageConfig.WHITE
            );

            MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path, config);

        } catch (Exception e) {
            throw new RuntimeException("Error generando imagen QR: " + e.getMessage(), e);
        }
    }

    private String shortenUrl(String longUrl) {
        try {
            String tinyUrlApi = "http://tinyurl.com/api-create.php?url=" + longUrl;
            URL url = new URL(tinyUrlApi);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            try (InputStream in = conn.getInputStream(); Scanner scanner = new Scanner(in)) {
                return scanner.useDelimiter("\\A").next();
            }
        } catch (Exception e) {
            return longUrl;
        }
    }

    // Iterador de códigos
    private static class CodeIterator implements Iterator<String> {
        private int prefixIndex = 0;
        private int number = 1;

        @Override
        public boolean hasNext() {
            return true; // infinito
        }

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
