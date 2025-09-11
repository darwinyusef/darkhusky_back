package com.darkhusky.darkhusky_back.infrastructure.persistence.jpa.adapter;

import com.darkhusky.darkhusky_back.domain.port.out.QrImageGeneratorPort;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import org.springframework.stereotype.Component;

import java.io.File;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

@Component
public class QrCodeImageGeneratorAdapter implements QrImageGeneratorPort {

    @Override
    public void generateQrImage(String code, String url) {
        try {
            String outputDir = "qrcodes";
            File directory = new File(outputDir);

            if (!directory.exists()) {
                directory.mkdirs();
            }

            String filePath = outputDir + File.separator + code + ".png";

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
}
