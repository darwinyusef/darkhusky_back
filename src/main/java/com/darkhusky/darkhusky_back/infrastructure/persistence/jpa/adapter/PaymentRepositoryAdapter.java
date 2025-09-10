package com.darkhusky.darkhusky_back.infrastructure.persistence.jpa.adapter;

import com.darkhusky.darkhusky_back.domain.model.Payment;
import com.darkhusky.darkhusky_back.domain.port.out.PaymentRepositoryPort;
import com.darkhusky.darkhusky_back.infrastructure.persistence.jpa.entity.OrderEntity;
import com.darkhusky.darkhusky_back.infrastructure.persistence.jpa.entity.PaymentEntity;
import com.darkhusky.darkhusky_back.infrastructure.persistence.jpa.repository.PaymentJpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class PaymentRepositoryAdapter implements PaymentRepositoryPort {

    private final PaymentJpaRepository repository;

    public PaymentRepositoryAdapter(PaymentJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public Payment save(Payment payment) {
        PaymentEntity entity = toEntity(payment);
        return toDomain(repository.save(entity));
    }

    @Override
    public Optional<Payment> findById(Long id) {
        return repository.findById(id).map(this::toDomain);
    }

    @Override
    public List<Payment> findByOrderId(Long orderId) {
        return repository.findByOrderId(orderId).stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    private PaymentEntity toEntity(Payment domain) {
        if (domain == null) return null;
        PaymentEntity entity = new PaymentEntity();
        entity.setId(domain.getId());
        if (domain.getOrderId() != null) {
            OrderEntity order = new OrderEntity();
            order.setId(domain.getOrderId());
            entity.setOrder(order);
        }
        entity.setMethod(domain.getMethod());
        entity.setStatus(domain.getStatus());
        entity.setWompiReference(domain.getWompiReference());
        entity.setPayloadJson(domain.getPayloadJson());
        entity.setDate(domain.getDate());
        return entity;
    }

    private Payment toDomain(PaymentEntity entity) {
        if (entity == null) return null;
        return new Payment(
                entity.getId(),
                entity.getOrder() != null ? entity.getOrder().getId() : null,
                entity.getMethod(),
                entity.getStatus(),
                entity.getWompiReference(),
                entity.getPayloadJson(),
                entity.getDate()
        );
    }
}
