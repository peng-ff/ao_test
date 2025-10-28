package com.square.checkin.controller;

import com.square.checkin.dto.request.CheckInRequest;
import com.square.checkin.dto.response.ApiResponse;
import com.square.checkin.dto.response.CheckInResponse;
import com.square.checkin.service.CheckInService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 签到控制器
 */
@Slf4j
@RestController
@RequestMapping("/api/checkin")
public class CheckInController {
    
    @Autowired
    private CheckInService checkInService;
    
    /**
     * 用户签到接口
     * POST /api/checkin
     */
    @PostMapping
    public ApiResponse<CheckInResponse> checkIn(@Valid @RequestBody CheckInRequest request) {
        log.info("收到签到请求, userId={}", request.getUserId());
        
        CheckInResponse response = checkInService.checkIn(request.getUserId());
        
        return ApiResponse.success("签到成功", response);
    }
}
