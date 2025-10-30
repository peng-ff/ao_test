package com.square.shopping.repository;

import com.square.shopping.entity.CartItem;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Cart item repository for database operations
 */
@Mapper
public interface CartItemRepository {
    
    @Insert("INSERT INTO cart_items (cart_id, product_id, quantity, price, created_at, updated_at) " +
            "VALUES (#{cartId}, #{productId}, #{quantity}, #{price}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(CartItem cartItem);
    
    @Update("UPDATE cart_items SET quantity = #{quantity}, price = #{price}, updated_at = NOW() WHERE id = #{id}")
    int update(CartItem cartItem);
    
    @Select("SELECT * FROM cart_items WHERE id = #{id}")
    CartItem findById(Long id);
    
    @Select("SELECT * FROM cart_items WHERE cart_id = #{cartId}")
    List<CartItem> findByCartId(Long cartId);
    
    @Select("SELECT * FROM cart_items WHERE cart_id = #{cartId} AND product_id = #{productId}")
    CartItem findByCartIdAndProductId(@Param("cartId") Long cartId, @Param("productId") Long productId);
    
    @Delete("DELETE FROM cart_items WHERE id = #{id}")
    int deleteById(Long id);
    
    @Delete("DELETE FROM cart_items WHERE cart_id = #{cartId}")
    int deleteByCartId(Long cartId);
}
