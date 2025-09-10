package com.darkhusky.darkhusky_back.application.service;

import com.darkhusky.darkhusky_back.domain.model.Product;
import com.darkhusky.darkhusky_back.domain.port.in.ProductUseCase;
import com.darkhusky.darkhusky_back.domain.port.out.ProductRepositoryPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService implements ProductUseCase {

    private final ProductRepositoryPort productRepositoryPort;

    public ProductService(ProductRepositoryPort productRepositoryPort) {
        this.productRepositoryPort = productRepositoryPort;
    }

    @Override
    @Transactional
    public Product createProduct(Product product) {
        product.setCreatedAt(LocalDateTime.now());
        // You can add more logic here, like validating category existence
        return productRepositoryPort.save(product);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Product> getProductById(Long id) {
        return productRepositoryPort.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Product> getAllProducts() {
        return productRepositoryPort.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Product> getProductsByCategory(Long categoryId) {
        return productRepositoryPort.findByCategoryId(categoryId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Product> getAvailableProducts() {
        return productRepositoryPort.findByStatus("AVAILABLE");
    }

    @Override
    @Transactional
    public Optional<Product> updateProduct(Long id, Product product) {
        return productRepositoryPort.findById(id).map(existingProduct -> {
            existingProduct.setName(product.getName());
            existingProduct.setDescription(product.getDescription());
            existingProduct.setPrice(product.getPrice());
            existingProduct.setStock(product.getStock());
            existingProduct.setCategoryId(product.getCategoryId());
            existingProduct.setStatus(product.getStatus());
            return productRepositoryPort.save(existingProduct);
        });
    }

    @Override
    @Transactional
    public boolean deleteProduct(Long id) {
        return productRepositoryPort.findById(id).map(product -> {
            productRepositoryPort.deleteById(id);
            return true;
        }).orElse(false);
    }
}
