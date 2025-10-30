package com.square.shopping.controller;

import com.square.shopping.dto.request.ProductRequest;
import com.square.shopping.dto.response.ApiResponse;
import com.square.shopping.dto.response.PageResponse;
import com.square.shopping.entity.Product;
import com.square.shopping.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Product controller for product management
 */
@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = "*")
public class ProductController {
    
    @Autowired
    private ProductService productService;
    
    /**
     * Get all products with pagination
     */
    @GetMapping
    public ApiResponse<PageResponse<Product>> getAllProducts(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int pageSize) {
        
        List<Product> products = productService.getAllProducts(page, pageSize);
        long total = productService.getTotalProductCount();
        
        PageResponse<Product> pageResponse = new PageResponse<>(products, page, pageSize, total);
        return ApiResponse.success(pageResponse);
    }
    
    /**
     * Get product by ID
     */
    @GetMapping("/{id}")
    public ApiResponse<Product> getProductById(@PathVariable Long id) {
        Product product = productService.getProductById(id);
        return ApiResponse.success(product);
    }
    
    /**
     * Get products by category
     */
    @GetMapping("/category/{categoryId}")
    public ApiResponse<PageResponse<Product>> getProductsByCategory(
            @PathVariable Long categoryId,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int pageSize) {
        
        List<Product> products = productService.getProductsByCategory(categoryId, page, pageSize);
        long total = productService.getProductCountByCategory(categoryId);
        
        PageResponse<Product> pageResponse = new PageResponse<>(products, page, pageSize, total);
        return ApiResponse.success(pageResponse);
    }
    
    /**
     * Search products
     */
    @GetMapping("/search")
    public ApiResponse<PageResponse<Product>> searchProducts(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int pageSize) {
        
        List<Product> products = productService.searchProducts(keyword, page, pageSize);
        long total = productService.getTotalProductCount();
        
        PageResponse<Product> pageResponse = new PageResponse<>(products, page, pageSize, total);
        return ApiResponse.success(pageResponse);
    }
    
    /**
     * Create product (Admin only)
     */
    @PostMapping
    public ApiResponse<Product> createProduct(@RequestBody ProductRequest request) {
        Product product = productService.createProduct(request);
        return ApiResponse.success("Product created successfully", product);
    }
    
    /**
     * Update product (Admin only)
     */
    @PutMapping("/{id}")
    public ApiResponse<Product> updateProduct(
            @PathVariable Long id,
            @RequestBody ProductRequest request) {
        Product product = productService.updateProduct(id, request);
        return ApiResponse.success("Product updated successfully", product);
    }
    
    /**
     * Delete product (Admin only)
     */
    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ApiResponse.success("Product deleted successfully", null);
    }
}
