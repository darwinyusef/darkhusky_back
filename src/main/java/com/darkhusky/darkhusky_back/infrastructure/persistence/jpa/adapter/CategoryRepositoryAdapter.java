package com.darkhusky.darkhusky_back.infrastructure.persistence.jpa.adapter;

import com.darkhusky.darkhusky_back.domain.model.Category;
import com.darkhusky.darkhusky_back.domain.port.out.CategoryRepositoryPort;
import com.darkhusky.darkhusky_back.infrastructure.persistence.jpa.entity.CategoryEntity;
import com.darkhusky.darkhusky_back.infrastructure.persistence.jpa.mapper.CategoryMapper;
import com.darkhusky.darkhusky_back.infrastructure.persistence.jpa.repository.CategoryJpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class CategoryRepositoryAdapter implements CategoryRepositoryPort {

    private final CategoryJpaRepository categoryJpaRepository;

    public CategoryRepositoryAdapter(CategoryJpaRepository categoryJpaRepository) {
        this.categoryJpaRepository = categoryJpaRepository;
    }

    @Override
    public Category save(Category category) {
        CategoryEntity categoryEntity = CategoryMapper.toEntity(category);
        CategoryEntity savedEntity = categoryJpaRepository.save(categoryEntity);
        return CategoryMapper.toDomain(savedEntity);
    }

    @Override
    public Optional<Category> findById(Long id) {
        return categoryJpaRepository.findById(id).map(CategoryMapper::toDomain);
    }

    @Override
    public Optional<Category> findBySlug(String slug) {
        return categoryJpaRepository.findBySlug(slug).map(CategoryMapper::toDomain);
    }

    @Override
    public List<Category> findByParentCategoryId(Long parentId) {
        return categoryJpaRepository.findByParentCategoryId(parentId).stream()
                .map(CategoryMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Category> findAll() {
        return categoryJpaRepository.findAll().stream()
                .map(CategoryMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        categoryJpaRepository.deleteById(id);
    }
}
