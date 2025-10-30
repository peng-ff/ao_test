package com.square.shopping.repository;

import com.square.shopping.entity.Order;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Order repository for database operations
 */
@Mapper
public interface OrderRepository {
    
    @Insert("INSERT INTO orders (order_number, user_id, total_amount, shipping_fee, discount_amount, final_amount, " +
            "status, shipping_address_id, payment_method, payment_status, order_date, created_at, updated_at) " +
            "VALUES (#{orderNumber}, #{userId}, #{totalAmount}, #{shippingFee}, #{discountAmount}, #{finalAmount}, " +
            "#{status}, #{shippingAddressId}, #{paymentMethod}, #{paymentStatus}, NOW(), NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Order order);
    
    @Update("UPDATE orders SET status = #{status}, payment_status = #{paymentStatus}, " +
            "tracking_number = #{trackingNumber}, updated_at = NOW() WHERE id = #{id}")
    int update(Order order);
    
    @Select("SELECT * FROM orders WHERE id = #{id}")
    Order findById(Long id);
    
    @Select("SELECT * FROM orders WHERE order_number = #{orderNumber}")
    Order findByOrderNumber(String orderNumber);
    
    @Select("SELECT * FROM orders WHERE user_id = #{userId} ORDER BY created_at DESC LIMIT #{limit} OFFSET #{offset}")
    List<Order> findByUserId(@Param("userId") Long userId, @Param("limit") int limit, @Param("offset") int offset);
    
    @Select("SELECT * FROM orders ORDER BY created_at DESC LIMIT #{limit} OFFSET #{offset}")
    List<Order> findAll(@Param("limit") int limit, @Param("offset") int offset);
    
    @Select("SELECT COUNT(*) FROM orders WHERE user_id = #{userId}")
    long countByUserId(Long userId);
    
    @Select("SELECT COUNT(*) FROM orders")
    long count();
    
    @Update("UPDATE orders SET payment_status = #{status}, paid_at = NOW(), updated_at = NOW() WHERE id = #{id}")
    int updatePaymentStatus(@Param("id") Long id, @Param("status") String status);
    
    @Update("UPDATE orders SET status = #{status}, shipped_at = NOW(), tracking_number = #{trackingNumber}, updated_at = NOW() WHERE id = #{id}")
    int markAsShipped(@Param("id") Long id, @Param("status") String status, @Param("trackingNumber") String trackingNumber);
    
    @Update("UPDATE orders SET status = #{status}, delivered_at = NOW(), updated_at = NOW() WHERE id = #{id}")
    int markAsDelivered(@Param("id") Long id, @Param("status") String status);
    
    @Update("UPDATE orders SET status = 'CANCELLED', cancelled_at = NOW(), cancel_reason = #{reason}, updated_at = NOW() WHERE id = #{id}")
    int cancel(@Param("id") Long id, @Param("reason") String reason);
}
