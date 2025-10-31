package com.square.checkin.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * 签到统计响应 DTO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StatsResponse {
    
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
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate lastCheckInDate;
}
