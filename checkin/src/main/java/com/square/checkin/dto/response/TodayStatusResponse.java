package com.square.checkin.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 今日签到状态响应 DTO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TodayStatusResponse {
    
    /**
     * 今日是否已签到
     */
    private Boolean hasCheckedIn;
    
    /**
     * 签到时间（已签到时返回）
     */
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    private LocalDateTime checkInTime;
    
    /**
     * 当前连续签到天数（已签到时返回）
     */
    private Integer continuousDays;
}
