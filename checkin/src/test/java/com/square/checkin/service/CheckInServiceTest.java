package com.square.checkin.service;

import com.square.checkin.dto.response.CheckInResponse;
import com.square.checkin.entity.CheckInRecord;
import com.square.checkin.entity.UserCheckInStats;
import com.square.checkin.exception.CheckInException;
import com.square.checkin.repository.CheckInRecordRepository;
import com.square.checkin.repository.UserCheckInStatsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

/**
 * CheckInService 单元测试
 */
class CheckInServiceTest {
    
    @Mock
    private CheckInRecordRepository checkInRecordRepository;
    
    @Mock
    private UserCheckInStatsRepository userCheckInStatsRepository;
    
    @InjectMocks
    private CheckInService checkInService;
    
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    
    /**
     * 测试首次签到
     */
    @Test
    void testCheckIn_FirstTime() {
        Long userId = 10001L;
        LocalDate today = LocalDate.now();
        
        // Mock 数据
        when(checkInRecordRepository.findByUserIdAndDate(userId, today)).thenReturn(null);
        when(userCheckInStatsRepository.findByUserId(userId)).thenReturn(null);
        when(userCheckInStatsRepository.insert(any())).thenReturn(1);
        when(checkInRecordRepository.insert(any())).thenReturn(1);
        when(userCheckInStatsRepository.update(any())).thenReturn(1);
        
        // 执行测试
        CheckInResponse response = checkInService.checkIn(userId);
        
        // 验证结果
        assertNotNull(response);
        assertEquals(userId, response.getUserId());
        assertEquals(1, response.getContinuousDays());
        assertEquals(1, response.getTotalDays());
        assertEquals(today, response.getCheckInDate());
        
        // 验证方法调用
        verify(checkInRecordRepository).findByUserIdAndDate(userId, today);
        verify(userCheckInStatsRepository).findByUserId(userId);
        verify(userCheckInStatsRepository).insert(any());
        verify(checkInRecordRepository).insert(any());
        verify(userCheckInStatsRepository).update(any());
    }
    
    /**
     * 测试连续签到
     */
    @Test
    void testCheckIn_Continuous() {
        Long userId = 10001L;
        LocalDate today = LocalDate.now();
        LocalDate yesterday = today.minusDays(1);
        
        UserCheckInStats stats = UserCheckInStats.builder()
                .userId(userId)
                .totalDays(5)
                .continuousDays(5)
                .maxContinuousDays(10)
                .lastCheckInDate(yesterday)
                .build();
        
        // Mock 数据
        when(checkInRecordRepository.findByUserIdAndDate(userId, today)).thenReturn(null);
        when(userCheckInStatsRepository.findByUserId(userId)).thenReturn(stats);
        when(checkInRecordRepository.insert(any())).thenReturn(1);
        when(userCheckInStatsRepository.update(any())).thenReturn(1);
        
        // 执行测试
        CheckInResponse response = checkInService.checkIn(userId);
        
        // 验证结果
        assertNotNull(response);
        assertEquals(6, response.getContinuousDays());
        assertEquals(6, response.getTotalDays());
        assertFalse(response.getIsNewRecord());
    }
    
    /**
     * 测试连续中断后签到
     */
    @Test
    void testCheckIn_AfterBreak() {
        Long userId = 10001L;
        LocalDate today = LocalDate.now();
        LocalDate lastCheckIn = today.minusDays(3);
        
        UserCheckInStats stats = UserCheckInStats.builder()
                .userId(userId)
                .totalDays(5)
                .continuousDays(5)
                .maxContinuousDays(10)
                .lastCheckInDate(lastCheckIn)
                .build();
        
        // Mock 数据
        when(checkInRecordRepository.findByUserIdAndDate(userId, today)).thenReturn(null);
        when(userCheckInStatsRepository.findByUserId(userId)).thenReturn(stats);
        when(checkInRecordRepository.insert(any())).thenReturn(1);
        when(userCheckInStatsRepository.update(any())).thenReturn(1);
        
        // 执行测试
        CheckInResponse response = checkInService.checkIn(userId);
        
        // 验证结果：连续天数应该重置为1
        assertNotNull(response);
        assertEquals(1, response.getContinuousDays());
        assertEquals(6, response.getTotalDays());
    }
    
    /**
     * 测试重复签到（应抛出异常）
     */
    @Test
    void testCheckIn_Duplicate() {
        Long userId = 10001L;
        LocalDate today = LocalDate.now();
        
        CheckInRecord existingRecord = CheckInRecord.builder()
                .id(1L)
                .userId(userId)
                .checkInDate(today)
                .checkInTime(LocalDateTime.now())
                .continuousDays(1)
                .build();
        
        // Mock 数据
        when(checkInRecordRepository.findByUserIdAndDate(userId, today)).thenReturn(existingRecord);
        
        // 执行测试并验证异常
        assertThrows(CheckInException.class, () -> checkInService.checkIn(userId));
        
        // 验证不应该执行插入操作
        verify(checkInRecordRepository, never()).insert(any());
        verify(userCheckInStatsRepository, never()).update(any());
    }
    
    /**
     * 测试无效用户ID
     */
    @Test
    void testCheckIn_InvalidUserId() {
        assertThrows(CheckInException.class, () -> checkInService.checkIn(null));
        assertThrows(CheckInException.class, () -> checkInService.checkIn(0L));
        assertThrows(CheckInException.class, () -> checkInService.checkIn(-1L));
    }
    
    /**
     * 测试刷新最长连续记录
     */
    @Test
    void testCheckIn_NewMaxContinuousRecord() {
        Long userId = 10001L;
        LocalDate today = LocalDate.now();
        LocalDate yesterday = today.minusDays(1);
        
        UserCheckInStats stats = UserCheckInStats.builder()
                .userId(userId)
                .totalDays(10)
                .continuousDays(10)
                .maxContinuousDays(10)
                .lastCheckInDate(yesterday)
                .build();
        
        // Mock 数据
        when(checkInRecordRepository.findByUserIdAndDate(userId, today)).thenReturn(null);
        when(userCheckInStatsRepository.findByUserId(userId)).thenReturn(stats);
        when(checkInRecordRepository.insert(any())).thenReturn(1);
        when(userCheckInStatsRepository.update(any())).thenReturn(1);
        
        // 执行测试
        CheckInResponse response = checkInService.checkIn(userId);
        
        // 验证结果：应该刷新最长连续记录
        assertNotNull(response);
        assertEquals(11, response.getContinuousDays());
        assertTrue(response.getIsNewRecord());
    }
}
