package com.darkhusky.darkhusky_back.infrastructure.persistence.jpa.repository;

import com.darkhusky.darkhusky_back.infrastructure.persistence.jpa.entity.StorageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StorageRepository extends JpaRepository<StorageEntity,Long> {
    List<StorageEntity> findByOwnerTypeAndOwnerId(String ownerType, Long ownerId);
}
