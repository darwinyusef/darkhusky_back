package com.darkhusky.darkhusky_back.infrastructure.persistence.jpa.adapter;

import com.darkhusky.darkhusky_back.domain.model.OrderItem;
import com.darkhusky.darkhusky_back.domain.port.out.OrderItemRepositoryPort;
import com.darkhusky.darkhusky_back.infrastructure.persistence.jpa.entity.OrderItemEntity;
import com.darkhusky.darkhusky_back.infrastructure.persistence.jpa.mapper.OrderItemMapper;
import com.darkhusky.darkhusky_back.infrastructure.persistence.jpa.repository.OrderItemJpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrderItemRepositoryAdapter implements OrderItemRepositoryPort {

    private final OrderItemJpaRepository orderItemJpaRepository;

    public OrderItemRepositoryAdapter(OrderItemJpaRepository orderItemJpaRepository) {
        this.orderItemJpaRepository = orderItemJpaRepository;
    }

    @Override
    public OrderItem save(OrderItem orderItem) {
        OrderItemEntity entity = OrderItemMapper.toEntity(orderItem);
        return OrderItemMapper.toDomain(orderItemJpaRepository.save(entity));
    }

    @Override
    public List<OrderItem> saveAll(List<OrderItem> orderItems) {
        List<OrderItemEntity> entities = orderItems.stream()
                .map(OrderItemMapper::toEntity)
                .collect(Collectors.toList());
        return orderItemJpaRepository.saveAll(entities).stream()
                .map(OrderItemMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<OrderItem> findByOrderId(Long orderId) {
        return orderItemJpaRepository.findByOrderId(orderId).stream()
                .map(OrderItemMapper::toDomain)
                .collect(Collectors.toList());
    }
}
