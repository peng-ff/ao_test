package com.square.checkin.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 签到记录实体类
 * 对应数据库表: check_in_record
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CheckInRecord {
    
    /**
     * 记录ID
     */
    private Long id;
    
    /**
     * 用户ID
     */
    private Long userId;
    
    /**
     * 签到日期
     */
    private LocalDate checkInDate;
    
    /**
     * 签到时间戳
     */
    private LocalDateTime checkInTime;
    
    /**
     * 签到时的连续天数
     */
    private Integer continuousDays;
    
    /**
     * 创建时间
     */
    private LocalDateTime createdAt;
}
