package com.darkhusky.darkhusky_back.infrastructure.web.controller;

import com.darkhusky.darkhusky_back.application.service.StorageService;
import com.darkhusky.darkhusky_back.domain.port.out.StoragePort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.Duration;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class StorageController {

    private final StorageService imageService;

    public StorageController(StorageService imageService) {
        this.imageService = imageService;
    }

    // Subida a través del backend (multipart)
    @PostMapping(path = "/{ownerType}/{ownerId}/images", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<List<StorageService.ImageDto>> uploadFiles(
            @PathVariable String ownerType,
            @PathVariable Long ownerId,
            @RequestPart("files") List<MultipartFile> files
    ) throws Exception {
        return ResponseEntity.status(HttpStatus.CREATED).body(imageService.addImages(ownerType, ownerId, files));
    }

    // Obtener lista
    @GetMapping("/{ownerType}/{ownerId}/images")
    public ResponseEntity<List<StorageService.ImageDto>> list(@PathVariable String ownerType, @PathVariable Long ownerId) throws Exception {
        return ResponseEntity.ok(imageService.listImages(ownerType, ownerId));
    }

    // Borrar
    @DeleteMapping("/images/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) throws Exception {
        imageService.deleteImage(id);
        return ResponseEntity.noContent().build();
    }

    // Generar presigned PUT para subida directa desde navegador
    @PostMapping("/{ownerType}/{ownerId}/images/presign")
    public ResponseEntity<Map<String, String>> presignUpload(
            @PathVariable String ownerType,
            @PathVariable Long ownerId,
            @RequestBody Map<String, String> body // { "filename": "...", "contentType": "image/png" }
    ) throws Exception {
        String filename = body.get("filename");
        String contentType = body.getOrDefault("contentType", "application/octet-stream");
        StoragePort.PresignedUpload presigned = imageService.generatePresignedUpload(ownerType, ownerId, filename, contentType, Duration.ofMinutes(15));
        return ResponseEntity.ok(Map.of("url", presigned.getUrl(), "objectKey", presigned.getObjectKey()));
    }
}
