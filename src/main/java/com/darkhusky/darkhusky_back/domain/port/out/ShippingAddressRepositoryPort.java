package com.darkhusky.darkhusky_back.domain.port.out;

import com.darkhusky.darkhusky_back.domain.model.ShippingAddress;

import java.util.Optional;

public interface ShippingAddressRepositoryPort {
    ShippingAddress save(ShippingAddress shippingAddress);
    Optional<ShippingAddress> findByOrderId(Long orderId);
}
