package com.darkhusky.darkhusky_back.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShippingAddress {
    private Long id;
    private Long orderId;
    private String recipientName;
    private String address;
    private String city;
    private String postalCode;
    private String country;
}
