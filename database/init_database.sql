-- ========================================
-- 用户签到系统数据库初始化脚本
-- ========================================

-- 创建数据库
CREATE DATABASE IF NOT EXISTS checkin_db 
    DEFAULT CHARACTER SET utf8mb4 
    DEFAULT COLLATE utf8mb4_unicode_ci;

USE checkin_db;

-- ========================================
-- 表1: 签到记录表 (check_in_record)
-- 用途: 记录每次用户签到的详细信息
-- ========================================
CREATE TABLE IF NOT EXISTS check_in_record (
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '记录ID',
    user_id BIGINT NOT NULL COMMENT '用户ID',
    check_in_date DATE NOT NULL COMMENT '签到日期',
    check_in_time DATETIME NOT NULL COMMENT '签到时间戳',
    continuous_days INT NOT NULL DEFAULT 1 COMMENT '签到时的连续天数',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    
    -- 唯一索引：防止同一天重复签到
    UNIQUE KEY uk_user_date (user_id, check_in_date),
    
    -- 普通索引：加速用户查询
    KEY idx_user_id (user_id),
    
    -- 普通索引：支持日期范围查询
    KEY idx_check_in_date (check_in_date)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='签到记录表';

-- ========================================
-- 表2: 用户签到统计表 (user_check_in_stats)
-- 用途: 维护每个用户的签到统计信息，便于快速查询
-- ========================================
CREATE TABLE IF NOT EXISTS user_check_in_stats (
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '记录ID',
    user_id BIGINT NOT NULL COMMENT '用户ID',
    total_days INT NOT NULL DEFAULT 0 COMMENT '累计签到天数',
    continuous_days INT NOT NULL DEFAULT 0 COMMENT '当前连续签到天数',
    max_continuous_days INT NOT NULL DEFAULT 0 COMMENT '最长连续签到天数',
    last_check_in_date DATE NULL COMMENT '最后签到日期',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    
    -- 唯一索引：每个用户只有一条统计记录
    UNIQUE KEY uk_user_id (user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户签到统计表';

-- ========================================
-- 初始化测试数据 (可选)
-- ========================================
-- 示例：插入一些测试数据
-- INSERT INTO user_check_in_stats (user_id, total_days, continuous_days, max_continuous_days, last_check_in_date)
-- VALUES (10001, 0, 0, 0, NULL);

-- ========================================
-- 数据库初始化完成
-- ========================================
