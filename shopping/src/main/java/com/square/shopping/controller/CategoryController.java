package com.square.shopping.controller;

import com.square.shopping.dto.response.ApiResponse;
import com.square.shopping.entity.Category;
import com.square.shopping.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Category controller for category management
 */
@RestController
@RequestMapping("/api/categories")
@CrossOrigin(origins = "*")
public class CategoryController {
    
    @Autowired
    private CategoryService categoryService;
    
    /**
     * Get all categories
     */
    @GetMapping
    public ApiResponse<List<Category>> getAllCategories() {
        List<Category> categories = categoryService.getAllCategories();
        return ApiResponse.success(categories);
    }
    
    /**
     * Get root categories
     */
    @GetMapping("/root")
    public ApiResponse<List<Category>> getRootCategories() {
        List<Category> categories = categoryService.getRootCategories();
        return ApiResponse.success(categories);
    }
    
    /**
     * Get category by ID
     */
    @GetMapping("/{id}")
    public ApiResponse<Category> getCategoryById(@PathVariable Long id) {
        Category category = categoryService.getCategoryById(id);
        return ApiResponse.success(category);
    }
    
    /**
     * Get subcategories
     */
    @GetMapping("/{id}/subcategories")
    public ApiResponse<List<Category>> getSubCategories(@PathVariable Long id) {
        List<Category> categories = categoryService.getSubCategories(id);
        return ApiResponse.success(categories);
    }
    
    /**
     * Create category (Admin only)
     */
    @PostMapping
    public ApiResponse<Category> createCategory(@RequestBody Category category) {
        Category created = categoryService.createCategory(category);
        return ApiResponse.success("Category created successfully", created);
    }
    
    /**
     * Update category (Admin only)
     */
    @PutMapping("/{id}")
    public ApiResponse<Category> updateCategory(
            @PathVariable Long id,
            @RequestBody Category category) {
        Category updated = categoryService.updateCategory(id, category);
        return ApiResponse.success("Category updated successfully", updated);
    }
    
    /**
     * Delete category (Admin only)
     */
    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return ApiResponse.success("Category deleted successfully", null);
    }
}
