package com.square.shopping.repository;

import com.square.shopping.entity.Cart;
import org.apache.ibatis.annotations.*;

/**
 * Cart repository for database operations
 */
@Mapper
public interface CartRepository {
    
    @Insert("INSERT INTO carts (user_id, created_at, updated_at) VALUES (#{userId}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Cart cart);
    
    @Select("SELECT * FROM carts WHERE id = #{id}")
    Cart findById(Long id);
    
    @Select("SELECT * FROM carts WHERE user_id = #{userId}")
    Cart findByUserId(Long userId);
    
    @Update("UPDATE carts SET updated_at = NOW() WHERE id = #{id}")
    int updateTimestamp(Long id);
    
    @Delete("DELETE FROM carts WHERE id = #{id}")
    int deleteById(Long id);
}
