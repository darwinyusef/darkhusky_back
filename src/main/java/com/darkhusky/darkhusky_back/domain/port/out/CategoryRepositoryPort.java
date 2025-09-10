package com.darkhusky.darkhusky_back.domain.port.out;

import com.darkhusky.darkhusky_back.domain.model.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryRepositoryPort {
    Category save(Category category);
    Optional<Category> findById(Long id);
    Optional<Category> findBySlug(String slug);
    List<Category> findByParentCategoryId(Long parentId);
    List<Category> findAll();
    void deleteById(Long id);
}
