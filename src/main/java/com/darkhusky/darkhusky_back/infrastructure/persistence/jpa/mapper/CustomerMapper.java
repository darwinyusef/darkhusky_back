package com.darkhusky.darkhusky_back.infrastructure.persistence.jpa.mapper;

import com.darkhusky.darkhusky_back.domain.model.Customer;
import com.darkhusky.darkhusky_back.infrastructure.persistence.jpa.entity.CustomerEntity;
import com.darkhusky.darkhusky_back.infrastructure.persistence.jpa.entity.UserEntity;

public class CustomerMapper {

    public static Customer toDomain(CustomerEntity entity) {
        if (entity == null) {
            return null;
        }
        return new Customer(
                entity.getId(),
                entity.getUser() != null ? entity.getUser().getUserId() : null,
                entity.getAddress(),
                entity.getPhoneNumber(),
                entity.getIdentityDocument()
        );
    }

    public static CustomerEntity toEntity(Customer domain) {
        if (domain == null) {
            return null;
        }
        CustomerEntity entity = new CustomerEntity();
        entity.setId(domain.getId());
        // In a real scenario, you'd fetch the UserEntity based on the userId
        if (domain.getUserId() != null) {
            UserEntity userEntity = new UserEntity();
            userEntity.setUserId(domain.getUserId());
            entity.setUser(userEntity);
        }
        entity.setAddress(domain.getAddress());
        entity.setPhoneNumber(domain.getPhoneNumber());
        entity.setIdentityDocument(domain.getIdentityDocument());
        return entity;
    }
}
