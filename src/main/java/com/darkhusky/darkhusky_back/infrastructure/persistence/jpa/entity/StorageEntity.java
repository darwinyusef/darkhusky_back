package com.darkhusky.darkhusky_back.infrastructure.persistence.jpa.entity;

import jakarta.persistence.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

@Entity
@Table(name = "storage")
public class StorageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="owner_type", nullable=false)
    private String ownerType;

    @Column(name="owner_id", nullable=false)
    private Long ownerId;

    @Column(name="object_key", nullable=false, length=512)
    private String objectKey;

    @Column(length = 255)
    private String filename;
    @Column(length = 100)
    private String contentType;
    private Long size;
    @Column(length = 1000)
    private String publicUrl;
    private LocalDateTime createdAt = LocalDateTime.now();

    @Autowired
    public StorageEntity(Long id, String ownerType, Long ownerId, String objectKey, String filename, String contentType, Long size, String publicUrl, LocalDateTime createdAt) {
        this.id = id;
        this.ownerType = ownerType;
        this.ownerId = ownerId;
        this.objectKey = objectKey;
        this.filename = filename;
        this.contentType = contentType;
        this.size = size;
        this.publicUrl = publicUrl;
        this.createdAt = createdAt;
    }

    public StorageEntity() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOwnerType() {
        return ownerType;
    }

    public void setOwnerType(String ownerType) {
        this.ownerType = ownerType;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public String getObjectKey() {
        return objectKey;
    }

    public void setObjectKey(String objectKey) {
        this.objectKey = objectKey;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public String getPublicUrl() {
        return publicUrl;
    }

    public void setPublicUrl(String publicUrl) {
        this.publicUrl = publicUrl;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
