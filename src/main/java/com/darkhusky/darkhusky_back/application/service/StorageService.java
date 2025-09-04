package com.darkhusky.darkhusky_back.application.service;

import com.darkhusky.darkhusky_back.domain.port.out.StoragePort;
import com.darkhusky.darkhusky_back.infrastructure.persistence.jpa.entity.StorageEntity;
import com.darkhusky.darkhusky_back.infrastructure.persistence.jpa.repository.StorageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.time.Duration;
import java.util.*;
import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class StorageService {

    private final StoragePort storage;
    private final StorageRepository imageRepository;
    private final long presignDurationMinutes;

    @Autowired
    public StorageService(StoragePort storage, StorageRepository imageRepository, @Value("${cloudflare.r2.presignDurationMinutes:60}") long presignDurationMinutes) {
        this.storage = storage;
        this.imageRepository = imageRepository;
        this.presignDurationMinutes = presignDurationMinutes;
    }

    @Transactional
    public List<ImageDto> addImages(String ownerType, Long ownerId, List<MultipartFile> files) throws Exception {
        List<ImageDto> saved = new ArrayList<>();
        for (MultipartFile file : files) {
            if (file.isEmpty()) continue;
            String original = Objects.requireNonNull(file.getOriginalFilename());
            String ext = getExtension(original).orElse("");
            String objectKey = String.format("%s/%d/%s%s", ownerType, ownerId, UUID.randomUUID(), ext.isEmpty() ? "" : "." + ext);
            try (InputStream is = file.getInputStream()) {
                storage.upload(objectKey, is, file.getSize(), file.getContentType());
            }
            StorageEntity e = new StorageEntity();
            e.setOwnerType(ownerType);
            e.setOwnerId(ownerId);
            e.setObjectKey(objectKey);
            e.setFilename(original);
            e.setContentType(file.getContentType());
            e.setSize(file.getSize());
            imageRepository.save(e);

            String presigned = storage.generatePresignedGetUrl(objectKey, Duration.ofMinutes(presignDurationMinutes));
            saved.add(new ImageDto(e.getId(), e.getFilename(), e.getContentType(), e.getSize(), objectKey, presigned));
        }
        return saved;
    }

    @Transactional(readOnly = true)
    public List<ImageDto> listImages(String ownerType, Long ownerId) throws Exception {
        return imageRepository.findByOwnerTypeAndOwnerId(ownerType, ownerId)
                .stream()
                .map(e -> {
                    String presigned = "";
                    try {
                        presigned = storage.generatePresignedGetUrl(e.getObjectKey(), Duration.ofMinutes(presignDurationMinutes));
                    } catch (Exception ex) {
                        // log
                    }
                    return new ImageDto(e.getId(), e.getFilename(), e.getContentType(), e.getSize(), e.getObjectKey(), presigned);
                })
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteImage(Long id) throws Exception {
        StorageEntity e = imageRepository.findById(id).orElseThrow(() -> new RuntimeException("Image not found"));
        storage.delete(e.getObjectKey());
        imageRepository.deleteById(id);
    }

    public StoragePort.PresignedUpload generatePresignedUpload(String ownerType, Long ownerId, String filename, String contentType, Duration duration) throws Exception {
        String ext = getExtension(filename).orElse("");
        String objectKey = String.format("%s/%d/%s%s", ownerType, ownerId, UUID.randomUUID(), ext.isEmpty() ? "" : "." + ext);
        return storage.generatePresignedPutUrl(objectKey, contentType, duration);
    }

    private Optional<String> getExtension(String filename) {
        int i = filename.lastIndexOf('.');
        if (i > 0 && i < filename.length() - 1) return Optional.of(filename.substring(i + 1));
        return Optional.empty();
    }

    // DTO
    public static class ImageDto {
        private Long id;
        private String filename;
        private String contentType;
        private Long size;
        private String objectKey;
        private String url;

        public ImageDto(Long id, String filename, String contentType, Long size, String objectKey, String url) {
            this.id = id; this.filename = filename; this.contentType = contentType; this.size = size; this.objectKey = objectKey; this.url = url;
        }
        // getters & setters
        public Long getId(){return id;}
        public String getFilename(){return filename;}
        public String getContentType(){return contentType;}
        public Long getSize(){return size;}
        public String getObjectKey(){return objectKey;}
        public String getUrl(){return url;}
    }
}
