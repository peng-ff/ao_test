package com.square.shopping.repository;

import com.square.shopping.entity.Product;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Product repository for database operations
 */
@Mapper
public interface ProductRepository {
    
    @Insert("INSERT INTO products (name, description, sku, price, original_price, stock_quantity, category_id, " +
            "image_url, active, sold_count, rating, created_at, updated_at) " +
            "VALUES (#{name}, #{description}, #{sku}, #{price}, #{originalPrice}, #{stockQuantity}, #{categoryId}, " +
            "#{imageUrl}, #{active}, 0, 0.0, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Product product);
    
    @Update("UPDATE products SET name = #{name}, description = #{description}, sku = #{sku}, price = #{price}, " +
            "original_price = #{originalPrice}, stock_quantity = #{stockQuantity}, category_id = #{categoryId}, " +
            "image_url = #{imageUrl}, active = #{active}, updated_at = NOW() WHERE id = #{id}")
    int update(Product product);
    
    @Select("SELECT * FROM products WHERE id = #{id}")
    Product findById(Long id);
    
    @Select("SELECT * FROM products WHERE sku = #{sku}")
    Product findBySku(String sku);
    
    @Select("SELECT * FROM products WHERE active = true ORDER BY created_at DESC LIMIT #{limit} OFFSET #{offset}")
    List<Product> findAll(@Param("limit") int limit, @Param("offset") int offset);
    
    @Select("SELECT * FROM products WHERE category_id = #{categoryId} AND active = true " +
            "ORDER BY created_at DESC LIMIT #{limit} OFFSET #{offset}")
    List<Product> findByCategoryId(@Param("categoryId") Long categoryId, @Param("limit") int limit, @Param("offset") int offset);
    
    @Select("SELECT * FROM products WHERE name LIKE CONCAT('%', #{keyword}, '%') AND active = true " +
            "ORDER BY created_at DESC LIMIT #{limit} OFFSET #{offset}")
    List<Product> searchByName(@Param("keyword") String keyword, @Param("limit") int limit, @Param("offset") int offset);
    
    @Select("SELECT COUNT(*) FROM products WHERE active = true")
    long count();
    
    @Select("SELECT COUNT(*) FROM products WHERE category_id = #{categoryId} AND active = true")
    long countByCategory(Long categoryId);
    
    @Update("UPDATE products SET stock_quantity = stock_quantity - #{quantity} WHERE id = #{id} AND stock_quantity >= #{quantity}")
    int decreaseStock(@Param("id") Long id, @Param("quantity") int quantity);
    
    @Update("UPDATE products SET stock_quantity = stock_quantity + #{quantity} WHERE id = #{id}")
    int increaseStock(@Param("id") Long id, @Param("quantity") int quantity);
    
    @Update("UPDATE products SET sold_count = sold_count + #{count} WHERE id = #{id}")
    int increaseSoldCount(@Param("id") Long id, @Param("count") int count);
    
    @Delete("DELETE FROM products WHERE id = #{id}")
    int deleteById(Long id);
}
