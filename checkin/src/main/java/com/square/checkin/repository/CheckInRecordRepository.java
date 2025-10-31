package com.square.checkin.repository;

import com.square.checkin.entity.CheckInRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;
import java.util.List;

/**
 * 签到记录数据访问接口
 */
@Mapper
public interface CheckInRecordRepository {
    
    /**
     * 插入签到记录
     */
    int insert(CheckInRecord record);
    
    /**
     * 根据用户ID和日期查询签到记录
     */
    CheckInRecord findByUserIdAndDate(@Param("userId") Long userId, @Param("checkInDate") LocalDate checkInDate);
    
    /**
     * 查询用户签到历史记录（分页）
     */
    List<CheckInRecord> findByUserId(
            @Param("userId") Long userId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            @Param("offset") Integer offset,
            @Param("limit") Integer limit
    );
    
    /**
     * 统计用户签到记录总数
     */
    long countByUserId(
            @Param("userId") Long userId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate
    );
    
    /**
     * 查询用户最近一次签到记录
     */
    CheckInRecord findLatestByUserId(@Param("userId") Long userId);
}
