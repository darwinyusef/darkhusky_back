package com.darkhusky.darkhusky_back.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductQrcode {
    private Long id;
    private Long productId;
    private String url;
    private LocalDateTime lastGeneratedAt;
}
