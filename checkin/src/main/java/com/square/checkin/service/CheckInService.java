package com.square.checkin.service;

import com.square.checkin.dto.response.CheckInResponse;
import com.square.checkin.entity.CheckInRecord;
import com.square.checkin.entity.UserCheckInStats;
import com.square.checkin.exception.CheckInException;
import com.square.checkin.repository.CheckInRecordRepository;
import com.square.checkin.repository.UserCheckInStatsRepository;
import com.square.checkin.util.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 签到服务类
 */
@Slf4j
@Service
public class CheckInService {
    
    @Autowired
    private CheckInRecordRepository checkInRecordRepository;
    
    @Autowired
    private UserCheckInStatsRepository userCheckInStatsRepository;
    
    /**
     * 用户签到
     * @param userId 用户ID
     * @return 签到响应
     */
    @Transactional(rollbackFor = Exception.class, timeout = 5)
    public CheckInResponse checkIn(Long userId) {
        log.info("用户签到开始, userId={}", userId);
        
        // 参数校验
        if (userId == null || userId <= 0) {
            throw CheckInException.invalidParameter("用户ID无效");
        }
        
        LocalDate today = DateUtil.getCurrentDate();
        LocalDateTime now = DateUtil.getCurrentDateTime();
        
        // 检查今日是否已签到
        CheckInRecord existingRecord = checkInRecordRepository.findByUserIdAndDate(userId, today);
        if (existingRecord != null) {
            log.warn("用户今日已签到, userId={}, date={}", userId, today);
            throw CheckInException.duplicateCheckIn();
        }
        
        // 获取或创建用户统计信息
        UserCheckInStats stats = userCheckInStatsRepository.findByUserId(userId);
        if (stats == null) {
            stats = createInitialStats(userId);
            userCheckInStatsRepository.insert(stats);
            log.info("创建用户统计信息, userId={}", userId);
        }
        
        // 计算连续签到天数
        int continuousDays = calculateContinuousDays(stats);
        log.debug("计算连续签到天数, userId={}, continuousDays={}", userId, continuousDays);
        
        // 创建签到记录
        CheckInRecord record = CheckInRecord.builder()
                .userId(userId)
                .checkInDate(today)
                .checkInTime(now)
                .continuousDays(continuousDays)
                .createdAt(now)
                .build();
        
        checkInRecordRepository.insert(record);
        log.info("签到记录已保存, userId={}, recordId={}", userId, record.getId());
        
        // 更新统计信息
        stats.setTotalDays(stats.getTotalDays() + 1);
        stats.setContinuousDays(continuousDays);
        stats.setLastCheckInDate(today);
        
        // 更新最长连续天数
        boolean isNewRecord = false;
        if (continuousDays > stats.getMaxContinuousDays()) {
            stats.setMaxContinuousDays(continuousDays);
            isNewRecord = true;
            log.info("刷新最长连续签到记录, userId={}, maxContinuousDays={}", userId, continuousDays);
        }
        
        stats.setUpdatedAt(now);
        userCheckInStatsRepository.update(stats);
        
        log.info("用户签到成功, userId={}, continuousDays={}, totalDays={}", 
                userId, continuousDays, stats.getTotalDays());
        
        // 构建响应
        return CheckInResponse.builder()
                .checkInId(record.getId())
                .userId(userId)
                .checkInDate(today)
                .checkInTime(now)
                .continuousDays(continuousDays)
                .totalDays(stats.getTotalDays())
                .isNewRecord(isNewRecord)
                .build();
    }
    
    /**
     * 计算连续签到天数
     */
    private int calculateContinuousDays(UserCheckInStats stats) {
        LocalDate lastCheckInDate = stats.getLastCheckInDate();
        
        // 首次签到
        if (lastCheckInDate == null) {
            return 1;
        }
        
        // 昨天有签到，连续天数+1
        if (DateUtil.isYesterday(lastCheckInDate)) {
            return stats.getContinuousDays() + 1;
        }
        
        // 连续中断，重置为1
        return 1;
    }
    
    /**
     * 创建初始统计信息
     */
    private UserCheckInStats createInitialStats(Long userId) {
        LocalDateTime now = DateUtil.getCurrentDateTime();
        return UserCheckInStats.builder()
                .userId(userId)
                .totalDays(0)
                .continuousDays(0)
                .maxContinuousDays(0)
                .lastCheckInDate(null)
                .createdAt(now)
                .updatedAt(now)
                .build();
    }
}
