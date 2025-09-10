package com.darkhusky.darkhusky_back.infrastructure.persistence.jpa.adapter;

import com.darkhusky.darkhusky_back.domain.model.OrderStatusHistory;
import com.darkhusky.darkhusky_back.domain.port.out.OrderStatusHistoryRepositoryPort;
import com.darkhusky.darkhusky_back.infrastructure.persistence.jpa.entity.OrderEntity;
import com.darkhusky.darkhusky_back.infrastructure.persistence.jpa.entity.OrderStatusHistoryEntity;
import com.darkhusky.darkhusky_back.infrastructure.persistence.jpa.repository.OrderStatusHistoryJpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrderStatusHistoryRepositoryAdapter implements OrderStatusHistoryRepositoryPort {

    private final OrderStatusHistoryJpaRepository repository;

    public OrderStatusHistoryRepositoryAdapter(OrderStatusHistoryJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public OrderStatusHistory save(OrderStatusHistory orderStatusHistory) {
        OrderStatusHistoryEntity entity = toEntity(orderStatusHistory);
        return toDomain(repository.save(entity));
    }

    @Override
    public List<OrderStatusHistory> findByOrderId(Long orderId) {
        return repository.findByOrderId(orderId).stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    private OrderStatusHistoryEntity toEntity(OrderStatusHistory domain) {
        if (domain == null) return null;
        OrderStatusHistoryEntity entity = new OrderStatusHistoryEntity();
        entity.setId(domain.getId());
        if (domain.getOrderId() != null) {
            OrderEntity order = new OrderEntity();
            order.setId(domain.getOrderId());
            entity.setOrder(order);
        }
        entity.setStatus(domain.getStatus());
        entity.setDate(domain.getDate());
        return entity;
    }

    private OrderStatusHistory toDomain(OrderStatusHistoryEntity entity) {
        if (entity == null) return null;
        return new OrderStatusHistory(
                entity.getId(),
                entity.getOrder() != null ? entity.getOrder().getId() : null,
                entity.getStatus(),
                entity.getDate()
        );
    }
}
