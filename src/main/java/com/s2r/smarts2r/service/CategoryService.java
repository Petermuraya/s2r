package com.s2r.smarts2r.service;

import com.s2r.smarts2r.dto.CategoryDTO;
import com.s2r.smarts2r.model.Category;
import com.s2r.smarts2r.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public List<CategoryDTO> getAllCategories() {
        return categoryRepository.findAll().stream()
            .map(this::mapToDTO)
            .collect(Collectors.toList());
    }

    public CategoryDTO getCategoryById(Long id) {
        return categoryRepository.findById(id)
            .map(this::mapToDTO)
            .orElseThrow(() -> new RuntimeException("Category not found"));
    }

    public CategoryDTO getCategoryBySlug(String slug) {
        return categoryRepository.findBySlug(slug)
            .map(this::mapToDTO)
            .orElseThrow(() -> new RuntimeException("Category not found"));
    }

    public CategoryDTO createCategory(CategoryDTO categoryDTO) {
        String slug = categoryDTO.getName().toLowerCase().replaceAll("[^a-z0-9]+", "-");
        
        Category category = Category.builder()
            .name(categoryDTO.getName())
            .description(categoryDTO.getDescription())
            .slug(slug)
            .iconUrl(categoryDTO.getIconUrl())
            .build();

        Category savedCategory = categoryRepository.save(category);
        return mapToDTO(savedCategory);
    }

    public CategoryDTO updateCategory(Long id, CategoryDTO categoryDTO) {
        Category category = categoryRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Category not found"));

        if (categoryDTO.getName() != null) {
            category.setName(categoryDTO.getName());
            String slug = categoryDTO.getName().toLowerCase().replaceAll("[^a-z0-9]+", "-");
            category.setSlug(slug);
        }
        if (categoryDTO.getDescription() != null) {
            category.setDescription(categoryDTO.getDescription());
        }
        if (categoryDTO.getIconUrl() != null) {
            category.setIconUrl(categoryDTO.getIconUrl());
        }
        category.setUpdatedAt(OffsetDateTime.now());

        Category updatedCategory = categoryRepository.save(category);
        return mapToDTO(updatedCategory);
    }

    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }

    private CategoryDTO mapToDTO(Category category) {
        return CategoryDTO.builder()
            .id(category.getId())
            .name(category.getName())
            .description(category.getDescription())
            .slug(category.getSlug())
            .iconUrl(category.getIconUrl())
            .createdAt(category.getCreatedAt())
            .updatedAt(category.getUpdatedAt())
            .build();
    }
}
