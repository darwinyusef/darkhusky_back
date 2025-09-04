package com.darkhusky.darkhusky_back.domain.port.out;

import java.io.InputStream;
import java.time.Duration;

public interface StoragePort {

    //Sube bytes y devuelve el objectKey (ruta en el bucket)
    String upload(String objectKey, InputStream data, long length, String contentType) throws Exception;

    //Borra un objeto por su clave
    void delete(String objectKey) throws Exception;

    //Genera URL presignada GET (para descargar) con duración dada
    String generatePresignedGetUrl(String objectKey, Duration duration) throws Exception;

    //Genera URL presignada PUT (para subir directamente desde navegador)
    // Devuelve el URL para hacer PUT y la objectKey (para guardarla luego)
    PresignedUpload generatePresignedPutUrl(String objectKey, String contentType, Duration duration) throws Exception;

    class PresignedUpload {
        private final String url;
        private final String objectKey;
        public PresignedUpload(String url, String objectKey) {
            this.url = url; this.objectKey = objectKey;
        }
        public String getUrl() { return url; }
        public String getObjectKey() { return objectKey; }
    }
}
