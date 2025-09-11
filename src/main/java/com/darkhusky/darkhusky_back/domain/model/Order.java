package com.darkhusky.darkhusky_back.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    private Long id;
    private Long userId;
    private String status;
    private BigDecimal total;
    private LocalDateTime createdAt;
    private LocalDateTime paidAt;
}
