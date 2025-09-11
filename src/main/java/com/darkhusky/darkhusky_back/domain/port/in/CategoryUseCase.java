package com.darkhusky.darkhusky_back.domain.port.in;

import com.darkhusky.darkhusky_back.domain.model.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryUseCase {
    Category createCategory(Category category);
    Optional<Category> getCategoryById(Long id);
    List<Category> getAllCategories();
    Optional<Category> updateCategory(Long id, Category category);
    boolean deleteCategory(Long id);
    List<Category> getSubCategories(Long parentId);
}
