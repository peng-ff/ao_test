package com.bus.ticketing.entity.enums;

/**
 * 使用状态枚举
 */
public enum UsageStatus {
    /**
     * 未使用
     */
    UNUSED,
    
    /**
     * 已使用
     */
    USED,
    
    /**
     * 已过期
     */
    EXPIRED,
    
    /**
     * 已退票
     */
    REFUNDED
}
