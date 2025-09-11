package com.darkhusky.darkhusky_back.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QrCode {

    public enum QrType {
        PRODUCT_DETAILS,
        USER_PROFILE,
        DELIVERY_TRACKING,
        OUT_OF_STOCK_ALERT
    }

    public enum QrStatus {
        ACTIVE,
        INACTIVE,
        EXPIRED
    }

    private Long id;
    private String qrCode;
    private String relatedEntityType;
    private Long relatedEntityId;
    private QrType type;
    private QrStatus status;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
}
