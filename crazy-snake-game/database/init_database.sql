-- 疯狂蟒蛇游戏数据库初始化脚本
-- 创建数据库
CREATE DATABASE IF NOT EXISTS crazy_snake_game CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE crazy_snake_game;

-- 用户表
CREATE TABLE IF NOT EXISTS users (
    user_id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '用户唯一标识',
    username VARCHAR(50) NOT NULL UNIQUE COMMENT '用户名',
    password VARCHAR(255) NOT NULL COMMENT '加密后的密码',
    email VARCHAR(100) UNIQUE COMMENT '邮箱地址',
    highest_score INT DEFAULT 0 COMMENT '历史最高分',
    total_games INT DEFAULT 0 COMMENT '总游戏局数',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    INDEX idx_username (username),
    INDEX idx_highest_score (highest_score DESC)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

-- 游戏记录表
CREATE TABLE IF NOT EXISTS game_records (
    record_id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '记录唯一标识',
    user_id BIGINT NOT NULL COMMENT '关联用户ID',
    score INT NOT NULL COMMENT '游戏得分',
    max_length INT NOT NULL COMMENT '蛇的最大长度',
    game_duration INT NOT NULL COMMENT '游戏时长（秒）',
    max_stage INT NOT NULL COMMENT '达到的最高阶段',
    items_collected INT DEFAULT 0 COMMENT '收集的道具数量',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '游戏时间',
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,
    INDEX idx_user_id (user_id),
    INDEX idx_score (score DESC),
    INDEX idx_created_at (created_at DESC)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='游戏记录表';

-- 道具配置表
CREATE TABLE IF NOT EXISTS item_configs (
    item_id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '道具唯一标识',
    item_type VARCHAR(50) NOT NULL UNIQUE COMMENT '道具类型标识',
    item_name VARCHAR(100) NOT NULL COMMENT '道具名称',
    effect_description VARCHAR(255) NOT NULL COMMENT '效果描述',
    duration INT COMMENT '持续时间（毫秒）',
    rarity VARCHAR(20) NOT NULL COMMENT '稀有度等级',
    spawn_weight INT NOT NULL COMMENT '生成权重',
    is_active BOOLEAN DEFAULT TRUE COMMENT '是否启用',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    INDEX idx_item_type (item_type),
    INDEX idx_is_active (is_active)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='道具配置表';

-- 插入道具配置初始数据
INSERT INTO item_configs (item_type, item_name, effect_description, duration, rarity, spawn_weight, is_active) VALUES
('SPEED_UP', '加速道具', '移动速度提升50%', 5000, 'COMMON', 20, TRUE),
('SPEED_DOWN', '减速道具', '移动速度降低30%', 5000, 'COMMON', 20, TRUE),
('DOUBLE_SCORE', '双倍积分', '获得分数翻倍', 10000, 'RARE', 10, TRUE),
('SHRINK_BODY', '缩短身体', '蛇身长度减少3节（最少保留3节）', NULL, 'COMMON', 15, TRUE),
('WALL_PASS', '穿墙模式', '可穿过边界和障碍物', 8000, 'RARE', 8, TRUE),
('REVERSE_CONTROL', '混乱控制', '方向键控制反转', 7000, 'RARE', 7, TRUE),
('INVINCIBLE', '无敌状态', '免疫所有碰撞伤害', 6000, 'EPIC', 3, TRUE);

-- 创建用于每日排行榜的视图
CREATE OR REPLACE VIEW daily_rankings AS
SELECT 
    u.user_id,
    u.username,
    MAX(gr.score) AS highest_score,
    COUNT(gr.record_id) AS games_played,
    DATE(gr.created_at) AS game_date
FROM users u
JOIN game_records gr ON u.user_id = gr.user_id
WHERE DATE(gr.created_at) = CURDATE()
GROUP BY u.user_id, u.username, DATE(gr.created_at)
ORDER BY highest_score DESC;

-- 创建用于全局排行榜的视图
CREATE OR REPLACE VIEW global_rankings AS
SELECT 
    u.user_id,
    u.username,
    u.highest_score,
    u.total_games
FROM users u
ORDER BY u.highest_score DESC;
