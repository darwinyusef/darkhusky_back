package com.darkhusky.darkhusky_back.infrastructure.persistence.jpa.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "product_variants")
public class ProductVariantEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private ProductEntity product;

    @Column(name = "variant_attribute", nullable = false)
    private String attribute;

    @Column(name = "variant_value", nullable = false)
    private String value;

    @Column(name = "extra_price", precision = 10, scale = 2)
    private BigDecimal extraPrice;
}
