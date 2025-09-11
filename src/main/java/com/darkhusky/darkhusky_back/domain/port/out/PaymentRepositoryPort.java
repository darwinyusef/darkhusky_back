package com.darkhusky.darkhusky_back.domain.port.out;

import com.darkhusky.darkhusky_back.domain.model.Payment;

import java.util.List;
import java.util.Optional;

public interface PaymentRepositoryPort {
    Payment save(Payment payment);
    Optional<Payment> findById(Long id);
    List<Payment> findByOrderId(Long orderId);
}
