package com.square.shopping.service;

import com.square.shopping.entity.Category;
import com.square.shopping.exception.BusinessException;
import com.square.shopping.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Category service for category management
 */
@Service
public class CategoryService {
    
    @Autowired
    private CategoryRepository categoryRepository;
    
    /**
     * Create a new category
     */
    @Transactional
    public Category createCategory(Category category) {
        categoryRepository.insert(category);
        return category;
    }
    
    /**
     * Update category
     */
    @Transactional
    public Category updateCategory(Long id, Category category) {
        Category existingCategory = categoryRepository.findById(id);
        if (existingCategory == null) {
            throw new BusinessException("Category not found");
        }
        
        category.setId(id);
        categoryRepository.update(category);
        return categoryRepository.findById(id);
    }
    
    /**
     * Get category by ID
     */
    public Category getCategoryById(Long id) {
        Category category = categoryRepository.findById(id);
        if (category == null) {
            throw new BusinessException("Category not found");
        }
        return category;
    }
    
    /**
     * Get all categories
     */
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }
    
    /**
     * Get root categories
     */
    public List<Category> getRootCategories() {
        return categoryRepository.findRootCategories();
    }
    
    /**
     * Get subcategories
     */
    public List<Category> getSubCategories(Long parentId) {
        return categoryRepository.findByParentId(parentId);
    }
    
    /**
     * Delete category
     */
    @Transactional
    public void deleteCategory(Long id) {
        Category category = categoryRepository.findById(id);
        if (category == null) {
            throw new BusinessException("Category not found");
        }
        categoryRepository.deleteById(id);
    }
}
