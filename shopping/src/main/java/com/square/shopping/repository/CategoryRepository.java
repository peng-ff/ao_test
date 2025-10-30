package com.square.shopping.repository;

import com.square.shopping.entity.Category;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Category repository for database operations
 */
@Mapper
public interface CategoryRepository {
    
    @Insert("INSERT INTO categories (name, description, parent_id, sort_order, active, created_at, updated_at) " +
            "VALUES (#{name}, #{description}, #{parentId}, #{sortOrder}, #{active}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Category category);
    
    @Update("UPDATE categories SET name = #{name}, description = #{description}, parent_id = #{parentId}, " +
            "sort_order = #{sortOrder}, active = #{active}, updated_at = NOW() WHERE id = #{id}")
    int update(Category category);
    
    @Select("SELECT * FROM categories WHERE id = #{id}")
    Category findById(Long id);
    
    @Select("SELECT * FROM categories WHERE active = true ORDER BY sort_order ASC")
    List<Category> findAll();
    
    @Select("SELECT * FROM categories WHERE parent_id IS NULL AND active = true ORDER BY sort_order ASC")
    List<Category> findRootCategories();
    
    @Select("SELECT * FROM categories WHERE parent_id = #{parentId} AND active = true ORDER BY sort_order ASC")
    List<Category> findByParentId(Long parentId);
    
    @Delete("DELETE FROM categories WHERE id = #{id}")
    int deleteById(Long id);
}
