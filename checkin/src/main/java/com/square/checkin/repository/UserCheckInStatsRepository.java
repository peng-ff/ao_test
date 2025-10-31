package com.square.checkin.repository;

import com.square.checkin.entity.UserCheckInStats;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 用户签到统计数据访问接口
 */
@Mapper
public interface UserCheckInStatsRepository {
    
    /**
     * 插入用户统计记录
     */
    int insert(UserCheckInStats stats);
    
    /**
     * 根据用户ID查询统计信息
     */
    UserCheckInStats findByUserId(@Param("userId") Long userId);
    
    /**
     * 更新用户统计信息
     */
    int update(UserCheckInStats stats);
    
    /**
     * 根据用户ID删除统计记录（用于测试）
     */
    int deleteByUserId(@Param("userId") Long userId);
}
