package com.square.checkin.service;

import com.square.checkin.dto.request.QueryRequest;
import com.square.checkin.dto.response.CheckInRecordDto;
import com.square.checkin.dto.response.HistoryResponse;
import com.square.checkin.dto.response.StatsResponse;
import com.square.checkin.dto.response.TodayStatusResponse;
import com.square.checkin.entity.CheckInRecord;
import com.square.checkin.entity.UserCheckInStats;
import com.square.checkin.repository.CheckInRecordRepository;
import com.square.checkin.repository.UserCheckInStatsRepository;
import com.square.checkin.util.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 签到统计查询服务类
 */
@Slf4j
@Service
public class CheckInStatisticsService {
    
    @Autowired
    private CheckInRecordRepository checkInRecordRepository;
    
    @Autowired
    private UserCheckInStatsRepository userCheckInStatsRepository;
    
    /**
     * 查询今日签到状态
     * @param userId 用户ID
     * @return 今日签到状态
     */
    @Transactional(readOnly = true)
    public TodayStatusResponse getTodayStatus(Long userId) {
        log.info("查询今日签到状态, userId={}", userId);
        
        LocalDate today = DateUtil.getCurrentDate();
        CheckInRecord record = checkInRecordRepository.findByUserIdAndDate(userId, today);
        
        if (record == null) {
            log.debug("用户今日未签到, userId={}", userId);
            return TodayStatusResponse.builder()
                    .hasCheckedIn(false)
                    .build();
        }
        
        log.info("用户今日已签到, userId={}, checkInTime={}", userId, record.getCheckInTime());
        return TodayStatusResponse.builder()
                .hasCheckedIn(true)
                .checkInTime(record.getCheckInTime())
                .continuousDays(record.getContinuousDays())
                .build();
    }
    
    /**
     * 查询用户签到统计信息
     * @param userId 用户ID
     * @return 签到统计信息
     */
    @Transactional(readOnly = true)
    public StatsResponse getStats(Long userId) {
        log.info("查询用户签到统计, userId={}", userId);
        
        UserCheckInStats stats = userCheckInStatsRepository.findByUserId(userId);
        
        if (stats == null) {
            log.debug("用户暂无签到统计, userId={}", userId);
            return StatsResponse.builder()
                    .userId(userId)
                    .totalDays(0)
                    .continuousDays(0)
                    .maxContinuousDays(0)
                    .lastCheckInDate(null)
                    .build();
        }
        
        log.info("查询用户签到统计成功, userId={}, totalDays={}, continuousDays={}", 
                userId, stats.getTotalDays(), stats.getContinuousDays());
        
        return StatsResponse.builder()
                .userId(userId)
                .totalDays(stats.getTotalDays())
                .continuousDays(stats.getContinuousDays())
                .maxContinuousDays(stats.getMaxContinuousDays())
                .lastCheckInDate(stats.getLastCheckInDate())
                .build();
    }
    
    /**
     * 查询用户签到历史记录（分页）
     * @param userId 用户ID
     * @param request 查询请求参数
     * @return 签到历史记录
     */
    @Transactional(readOnly = true)
    public HistoryResponse getHistory(Long userId, QueryRequest request) {
        log.info("查询用户签到历史, userId={}, page={}, pageSize={}", 
                userId, request.getPage(), request.getPageSize());
        
        // 校验并调整分页参数
        int page = request.getPage() != null && request.getPage() > 0 ? request.getPage() : 1;
        int pageSize = request.getPageSize() != null && request.getPageSize() > 0 
                ? Math.min(request.getPageSize(), 100) : 20;
        
        // 校验日期范围
        LocalDate startDate = request.getStartDate();
        LocalDate endDate = request.getEndDate();
        
        if (startDate != null && endDate != null && startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("开始日期不能晚于结束日期");
        }
        
        // 计算偏移量
        int offset = (page - 1) * pageSize;
        
        // 查询总记录数
        long total = checkInRecordRepository.countByUserId(userId, startDate, endDate);
        log.debug("签到历史记录总数, userId={}, total={}", userId, total);
        
        // 查询记录列表
        List<CheckInRecord> records = checkInRecordRepository.findByUserId(
                userId, startDate, endDate, offset, pageSize);
        
        // 转换为DTO
        List<CheckInRecordDto> recordDtos = records.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
        
        log.info("查询用户签到历史成功, userId={}, total={}, returnSize={}", 
                userId, total, recordDtos.size());
        
        return HistoryResponse.builder()
                .total(total)
                .page(page)
                .pageSize(pageSize)
                .records(recordDtos)
                .build();
    }
    
    /**
     * 转换为DTO
     */
    private CheckInRecordDto convertToDto(CheckInRecord record) {
        return CheckInRecordDto.builder()
                .checkInId(record.getId())
                .checkInDate(record.getCheckInDate())
                .checkInTime(record.getCheckInTime())
                .continuousDays(record.getContinuousDays())
                .build();
    }
}
