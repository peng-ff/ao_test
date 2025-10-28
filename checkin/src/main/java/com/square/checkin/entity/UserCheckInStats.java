package com.square.checkin.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 用户签到统计实体类
 * 对应数据库表: user_check_in_stats
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserCheckInStats {
    
    /**
     * 记录ID
     */
    private Long id;
    
    /**
     * 用户ID
     */
    private Long userId;
    
    /**
     * 累计签到天数
     */
    private Integer totalDays;
    
    /**
     * 当前连续签到天数
     */
    private Integer continuousDays;
    
    /**
     * 最长连续签到天数
     */
    private Integer maxContinuousDays;
    
    /**
     * 最后签到日期
     */
    private LocalDate lastCheckInDate;
    
    /**
     * 创建时间
     */
    private LocalDateTime createdAt;
    
    /**
     * 更新时间
     */
    private LocalDateTime updatedAt;
}
