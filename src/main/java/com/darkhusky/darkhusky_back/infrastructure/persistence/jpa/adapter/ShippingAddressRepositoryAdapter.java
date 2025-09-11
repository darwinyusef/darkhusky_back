package com.darkhusky.darkhusky_back.infrastructure.persistence.jpa.adapter;

import com.darkhusky.darkhusky_back.domain.model.ShippingAddress;
import com.darkhusky.darkhusky_back.domain.port.out.ShippingAddressRepositoryPort;
import com.darkhusky.darkhusky_back.infrastructure.persistence.jpa.entity.OrderEntity;
import com.darkhusky.darkhusky_back.infrastructure.persistence.jpa.entity.ShippingAddressEntity;
import com.darkhusky.darkhusky_back.infrastructure.persistence.jpa.repository.ShippingAddressJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ShippingAddressRepositoryAdapter implements ShippingAddressRepositoryPort {

    private final ShippingAddressJpaRepository repository;

    public ShippingAddressRepositoryAdapter(ShippingAddressJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public ShippingAddress save(ShippingAddress shippingAddress) {
        ShippingAddressEntity entity = toEntity(shippingAddress);
        return toDomain(repository.save(entity));
    }

    @Override
    public Optional<ShippingAddress> findByOrderId(Long orderId) {
        return repository.findByOrderId(orderId).map(this::toDomain);
    }

    private ShippingAddressEntity toEntity(ShippingAddress domain) {
        if (domain == null) return null;
        ShippingAddressEntity entity = new ShippingAddressEntity();
        entity.setId(domain.getId());
        if (domain.getOrderId() != null) {
            OrderEntity order = new OrderEntity();
            order.setId(domain.getOrderId());
            entity.setOrder(order);
        }
        entity.setRecipientName(domain.getRecipientName());
        entity.setAddress(domain.getAddress());
        entity.setCity(domain.getCity());
        entity.setPostalCode(domain.getPostalCode());
        entity.setCountry(domain.getCountry());
        return entity;
    }

    private ShippingAddress toDomain(ShippingAddressEntity entity) {
        if (entity == null) return null;
        return new ShippingAddress(
                entity.getId(),
                entity.getOrder() != null ? entity.getOrder().getId() : null,
                entity.getRecipientName(),
                entity.getAddress(),
                entity.getCity(),
                entity.getPostalCode(),
                entity.getCountry()
        );
    }
}
