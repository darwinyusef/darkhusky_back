package com.darkhusky.darkhusky_back.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Payment {
    private Long id;
    private Long orderId;
    private String method;
    private String status;
    private String wompiReference;
    private String payloadJson;
    private LocalDateTime date;
}
