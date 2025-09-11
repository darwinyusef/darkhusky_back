package com.darkhusky.darkhusky_back.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Customer {
    private Long id;
    private Long userId; // Corresponds to Usuario.id
    private String address;
    private String phoneNumber;
    private String identityDocument;
}
