package com.darkhusky.darkhusky_back.application.service;

import com.darkhusky.darkhusky_back.domain.model.Category;
import com.darkhusky.darkhusky_back.domain.port.in.CategoryUseCase;
import com.darkhusky.darkhusky_back.domain.port.out.CategoryRepositoryPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService implements CategoryUseCase {

    private final CategoryRepositoryPort categoryRepositoryPort;

    public CategoryService(CategoryRepositoryPort categoryRepositoryPort) {
        this.categoryRepositoryPort = categoryRepositoryPort;
    }

    @Override
    @Transactional
    public Category createCategory(Category category) {
        // Here you could add logic to generate the slug from the name
        return categoryRepositoryPort.save(category);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Category> getCategoryById(Long id) {
        return categoryRepositoryPort.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Category> getAllCategories() {
        return categoryRepositoryPort.findAll();
    }

    @Override
    @Transactional
    public Optional<Category> updateCategory(Long id, Category category) {
        return categoryRepositoryPort.findById(id).map(existingCategory -> {
            existingCategory.setName(category.getName());
            existingCategory.setSlug(category.getSlug());
            existingCategory.setDescription(category.getDescription());
            existingCategory.setParentCategoryId(category.getParentCategoryId());
            return categoryRepositoryPort.save(existingCategory);
        });
    }

    @Override
    @Transactional
    public boolean deleteCategory(Long id) {
        return categoryRepositoryPort.findById(id).map(category -> {
            categoryRepositoryPort.deleteById(id);
            return true;
        }).orElse(false);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Category> getSubCategories(Long parentId) {
        return categoryRepositoryPort.findByParentCategoryId(parentId);
    }
}
