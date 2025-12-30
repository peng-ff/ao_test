package com.bus.ticketing.repository;

import com.bus.ticketing.entity.Order;
import com.bus.ticketing.entity.enums.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * 订单数据访问层
 */
@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    
    /**
     * 根据订单号查找
     */
    Optional<Order> findByOrderNumber(String orderNumber);
    
    /**
     * 根据用户ID查找订单
     */
    List<Order> findByUserIdOrderByCreatedAtDesc(Long userId);
    
    /**
     * 根据用户ID和支付状态查找订单
     */
    List<Order> findByUserIdAndPaymentStatusOrderByCreatedAtDesc(Long userId, PaymentStatus paymentStatus);
}
