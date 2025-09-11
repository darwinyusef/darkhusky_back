package com.darkhusky.darkhusky_back.infrastructure.persistence.jpa.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "payments")
public class PaymentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private OrderEntity order;

    @Column(nullable = false)
    private String method;

    @Column(nullable = false)
    private String status;

    @Column(name = "wompi_reference")
    private String wompiReference;

    @Lob
    @Column(name = "payload_json")
    private String payloadJson;

    @Column(nullable = false)
    private LocalDateTime date;
}
