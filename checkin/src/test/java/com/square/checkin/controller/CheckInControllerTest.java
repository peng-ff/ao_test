package com.square.checkin.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.square.checkin.dto.request.CheckInRequest;
import com.square.checkin.dto.response.CheckInResponse;
import com.square.checkin.service.CheckInService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * CheckInController 集成测试
 */
@WebMvcTest(CheckInController.class)
class CheckInControllerTest {
    
    @Autowired
    private MockMvc mockMvc;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    @MockBean
    private CheckInService checkInService;
    
    /**
     * 测试签到接口
     */
    @Test
    void testCheckIn_Success() throws Exception {
        Long userId = 10001L;
        
        CheckInResponse response = CheckInResponse.builder()
                .checkInId(1L)
                .userId(userId)
                .checkInDate(LocalDate.now())
                .checkInTime(LocalDateTime.now())
                .continuousDays(1)
                .totalDays(1)
                .isNewRecord(false)
                .build();
        
        when(checkInService.checkIn(any())).thenReturn(response);
        
        CheckInRequest request = CheckInRequest.builder()
                .userId(userId)
                .build();
        
        mockMvc.perform(post("/api/checkin")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("签到成功"))
                .andExpect(jsonPath("$.data.userId").value(userId))
                .andExpect(jsonPath("$.data.continuousDays").value(1));
    }
    
    /**
     * 测试签到接口 - 参数校验失败
     */
    @Test
    void testCheckIn_InvalidParameter() throws Exception {
        CheckInRequest request = CheckInRequest.builder()
                .userId(null)
                .build();
        
        mockMvc.perform(post("/api/checkin")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }
}
