package com.bus.ticketing.entity.enums;

/**
 * 支付状态枚举
 */
public enum PaymentStatus {
    /**
     * 待支付
     */
    PENDING,
    
    /**
     * 已支付
     */
    PAID,
    
    /**
     * 已取消
     */
    CANCELLED,
    
    /**
     * 已退款
     */
    REFUNDED
}
