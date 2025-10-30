package com.square.shopping.repository;

import com.square.shopping.entity.OrderItem;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Order item repository for database operations
 */
@Mapper
public interface OrderItemRepository {
    
    @Insert("INSERT INTO order_items (order_id, product_id, product_name, product_sku, price, quantity, subtotal, created_at) " +
            "VALUES (#{orderId}, #{productId}, #{productName}, #{productSku}, #{price}, #{quantity}, #{subtotal}, NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(OrderItem orderItem);
    
    @Select("SELECT * FROM order_items WHERE id = #{id}")
    OrderItem findById(Long id);
    
    @Select("SELECT * FROM order_items WHERE order_id = #{orderId}")
    List<OrderItem> findByOrderId(Long orderId);
    
    @Delete("DELETE FROM order_items WHERE id = #{id}")
    int deleteById(Long id);
}
