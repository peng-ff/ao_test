package com.square.shopping.repository;

import com.square.shopping.entity.Payment;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Payment repository for database operations
 */
@Mapper
public interface PaymentRepository {
    
    @Insert("INSERT INTO payments (order_id, payment_number, amount, payment_method, status, created_at, updated_at) " +
            "VALUES (#{orderId}, #{paymentNumber}, #{amount}, #{paymentMethod}, #{status}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Payment payment);
    
    @Update("UPDATE payments SET status = #{status}, transaction_id = #{transactionId}, " +
            "gateway_response = #{gatewayResponse}, updated_at = NOW() WHERE id = #{id}")
    int update(Payment payment);
    
    @Select("SELECT * FROM payments WHERE id = #{id}")
    Payment findById(Long id);
    
    @Select("SELECT * FROM payments WHERE order_id = #{orderId}")
    List<Payment> findByOrderId(Long orderId);
    
    @Select("SELECT * FROM payments WHERE payment_number = #{paymentNumber}")
    Payment findByPaymentNumber(String paymentNumber);
    
    @Update("UPDATE payments SET status = 'COMPLETED', paid_at = NOW(), updated_at = NOW() WHERE id = #{id}")
    int markAsPaid(Long id);
    
    @Update("UPDATE payments SET status = 'REFUNDED', refunded_at = NOW(), updated_at = NOW() WHERE id = #{id}")
    int markAsRefunded(Long id);
}
