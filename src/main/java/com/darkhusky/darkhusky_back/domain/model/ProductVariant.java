package com.darkhusky.darkhusky_back.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductVariant {
    private Long id;
    private Long productId;
    private String attribute;
    private String value;
    private BigDecimal extraPrice;
}
