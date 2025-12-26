package com.tokohobby.blogs.service;

import com.tokohobby.blogs.dto.CategoryRequest;
import com.tokohobby.blogs.entity.Category;
import com.tokohobby.blogs.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Transactional(readOnly = true)
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Transactional
    public Category createCategory(CategoryRequest request) {
        log.info("Creating new category: {}", request.getName());
        
        String slug = request.getName().toLowerCase().replaceAll("\\s+", "-");
        
        Category category = Category.builder()
                .name(request.getName())
                .slug(slug)
                .build();
        
        return categoryRepository.save(category);
    }

    @Transactional
    public void deleteCategory(Long id) {
        log.warn("Deleting category with id: {}", id);
        if (!categoryRepository.existsById(id)) {
            throw new NoSuchElementException("Category not found with id: " + id);
        }
        categoryRepository.deleteById(id);
    }
}
