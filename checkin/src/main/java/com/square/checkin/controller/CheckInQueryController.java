package com.square.checkin.controller;

import com.square.checkin.dto.request.QueryRequest;
import com.square.checkin.dto.response.ApiResponse;
import com.square.checkin.dto.response.HistoryResponse;
import com.square.checkin.dto.response.StatsResponse;
import com.square.checkin.dto.response.TodayStatusResponse;
import com.square.checkin.service.CheckInStatisticsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Positive;
import java.time.LocalDate;

/**
 * 签到查询控制器
 */
@Slf4j
@RestController
@RequestMapping("/api/checkin")
public class CheckInQueryController {
    
    @Autowired
    private CheckInStatisticsService statisticsService;
    
    /**
     * 查询今日签到状态
     * GET /api/checkin/today/{userId}
     */
    @GetMapping("/today/{userId}")
    public ApiResponse<TodayStatusResponse> getTodayStatus(
            @PathVariable @Positive(message = "用户ID必须为正整数") Long userId) {
        log.info("查询今日签到状态, userId={}", userId);
        
        TodayStatusResponse response = statisticsService.getTodayStatus(userId);
        
        return ApiResponse.success("查询成功", response);
    }
    
    /**
     * 查询用户签到统计
     * GET /api/checkin/stats/{userId}
     */
    @GetMapping("/stats/{userId}")
    public ApiResponse<StatsResponse> getStats(
            @PathVariable @Positive(message = "用户ID必须为正整数") Long userId) {
        log.info("查询用户签到统计, userId={}", userId);
        
        StatsResponse response = statisticsService.getStats(userId);
        
        return ApiResponse.success("查询成功", response);
    }
    
    /**
     * 查询签到历史记录
     * GET /api/checkin/history/{userId}
     */
    @GetMapping("/history/{userId}")
    public ApiResponse<HistoryResponse> getHistory(
            @PathVariable @Positive(message = "用户ID必须为正整数") Long userId,
            @RequestParam(required = false, defaultValue = "1") Integer page,
            @RequestParam(required = false, defaultValue = "20") Integer pageSize,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        
        log.info("查询签到历史, userId={}, page={}, pageSize={}, startDate={}, endDate={}", 
                userId, page, pageSize, startDate, endDate);
        
        QueryRequest request = QueryRequest.builder()
                .page(page)
                .pageSize(pageSize)
                .startDate(startDate)
                .endDate(endDate)
                .build();
        
        HistoryResponse response = statisticsService.getHistory(userId, request);
        
        return ApiResponse.success("查询成功", response);
    }
}
