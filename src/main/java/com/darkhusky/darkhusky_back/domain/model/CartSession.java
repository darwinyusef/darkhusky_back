package com.darkhusky.darkhusky_back.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartSession {
    private Long id;
    private Long userId;
    private String sessionToken;
    private LocalDateTime createdAt;
}
