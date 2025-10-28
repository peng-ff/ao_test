package com.square.checkin.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 签到记录 DTO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CheckInRecordDto {
    
    /**
     * 签到记录ID
     */
    private Long checkInId;
    
    /**
     * 签到日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate checkInDate;
    
    /**
     * 签到时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    private LocalDateTime checkInTime;
    
    /**
     * 签到时的连续天数
     */
    private Integer continuousDays;
}
