package com.darkhusky.darkhusky_back.domain.port.in;

import com.darkhusky.darkhusky_back.domain.model.Product;

import java.util.List;
import java.util.Optional;

public interface ProductUseCase {
    Product createProduct(Product product);
    Optional<Product> getProductById(Long id);
    List<Product> getAllProducts();
    List<Product> getProductsByCategory(Long categoryId);
    List<Product> getAvailableProducts(); // "Mostrar productos disponibles"
    Optional<Product> updateProduct(Long id, Product product);
    boolean deleteProduct(Long id);
    // You could add methods for managing variants, images, etc. here later
}
