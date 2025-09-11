package com.darkhusky.darkhusky_back.domain.port.out;

public interface QrImageGeneratorPort {
    void generateQrImage(String code, String url);
}
