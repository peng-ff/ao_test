package com.square.shopping.service;

import com.square.shopping.dto.request.ProductRequest;
import com.square.shopping.entity.Product;
import com.square.shopping.exception.BusinessException;
import com.square.shopping.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Product service for product management
 */
@Service
public class ProductService {
    
    @Autowired
    private ProductRepository productRepository;
    
    /**
     * Create a new product
     */
    @Transactional
    public Product createProduct(ProductRequest request) {
        // Check if SKU already exists
        if (productRepository.findBySku(request.getSku()) != null) {
            throw new BusinessException("Product SKU already exists");
        }
        
        Product product = new Product();
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setSku(request.getSku());
        product.setPrice(request.getPrice());
        product.setOriginalPrice(request.getOriginalPrice());
        product.setStockQuantity(request.getStockQuantity());
        product.setCategoryId(request.getCategoryId());
        product.setImageUrl(request.getImageUrl());
        product.setActive(request.getActive() != null ? request.getActive() : true);
        
        productRepository.insert(product);
        return product;
    }
    
    /**
     * Update product
     */
    @Transactional
    public Product updateProduct(Long id, ProductRequest request) {
        Product existingProduct = productRepository.findById(id);
        if (existingProduct == null) {
            throw new BusinessException("Product not found");
        }
        
        existingProduct.setName(request.getName());
        existingProduct.setDescription(request.getDescription());
        existingProduct.setPrice(request.getPrice());
        existingProduct.setOriginalPrice(request.getOriginalPrice());
        existingProduct.setStockQuantity(request.getStockQuantity());
        existingProduct.setCategoryId(request.getCategoryId());
        existingProduct.setImageUrl(request.getImageUrl());
        existingProduct.setActive(request.getActive());
        
        productRepository.update(existingProduct);
        return productRepository.findById(id);
    }
    
    /**
     * Get product by ID
     */
    public Product getProductById(Long id) {
        Product product = productRepository.findById(id);
        if (product == null) {
            throw new BusinessException("Product not found");
        }
        return product;
    }
    
    /**
     * Get all products with pagination
     */
    public List<Product> getAllProducts(int page, int pageSize) {
        int offset = (page - 1) * pageSize;
        return productRepository.findAll(pageSize, offset);
    }
    
    /**
     * Get products by category with pagination
     */
    public List<Product> getProductsByCategory(Long categoryId, int page, int pageSize) {
        int offset = (page - 1) * pageSize;
        return productRepository.findByCategoryId(categoryId, pageSize, offset);
    }
    
    /**
     * Search products by name
     */
    public List<Product> searchProducts(String keyword, int page, int pageSize) {
        int offset = (page - 1) * pageSize;
        return productRepository.searchByName(keyword, pageSize, offset);
    }
    
    /**
     * Get total product count
     */
    public long getTotalProductCount() {
        return productRepository.count();
    }
    
    /**
     * Get product count by category
     */
    public long getProductCountByCategory(Long categoryId) {
        return productRepository.countByCategory(categoryId);
    }
    
    /**
     * Delete product
     */
    @Transactional
    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id);
        if (product == null) {
            throw new BusinessException("Product not found");
        }
        productRepository.deleteById(id);
    }
    
    /**
     * Check and decrease stock
     */
    @Transactional
    public boolean decreaseStock(Long productId, int quantity) {
        Product product = productRepository.findById(productId);
        if (product == null) {
            throw new BusinessException("Product not found");
        }
        
        if (product.getStockQuantity() < quantity) {
            throw new BusinessException("Insufficient stock for product: " + product.getName());
        }
        
        int affected = productRepository.decreaseStock(productId, quantity);
        return affected > 0;
    }
    
    /**
     * Increase stock
     */
    @Transactional
    public void increaseStock(Long productId, int quantity) {
        productRepository.increaseStock(productId, quantity);
    }
}
