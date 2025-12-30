-- 公交车售票系统数据库初始化脚本

-- 创建数据库
CREATE DATABASE IF NOT EXISTS bus_ticketing_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE bus_ticketing_db;

-- 用户表
CREATE TABLE IF NOT EXISTS `user` (
    `id` BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '用户ID',
    `phone` VARCHAR(20) NOT NULL UNIQUE COMMENT '手机号',
    `name` VARCHAR(50) COMMENT '姓名',
    `password` VARCHAR(255) COMMENT '密码(加密)',
    `identity_type` VARCHAR(20) NOT NULL DEFAULT 'REGULAR' COMMENT '身份类型:REGULAR-普通,STUDENT-学生,SENIOR-老年人',
    `account_status` VARCHAR(20) NOT NULL DEFAULT 'ACTIVE' COMMENT '账户状态:ACTIVE-正常,FROZEN-冻结',
    `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '注册时间',
    `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    INDEX idx_phone (`phone`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

-- 线路表
CREATE TABLE IF NOT EXISTS `route` (
    `id` BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '线路ID',
    `route_name` VARCHAR(50) NOT NULL COMMENT '线路名称',
    `route_number` VARCHAR(20) NOT NULL UNIQUE COMMENT '线路编号',
    `start_station` VARCHAR(100) NOT NULL COMMENT '起点站',
    `end_station` VARCHAR(100) NOT NULL COMMENT '终点站',
    `operation_start_time` TIME NOT NULL COMMENT '运营开始时间',
    `operation_end_time` TIME NOT NULL COMMENT '运营结束时间',
    `route_status` VARCHAR(20) NOT NULL DEFAULT 'OPERATING' COMMENT '线路状态:OPERATING-运营中,SUSPENDED-停运,MAINTENANCE-维护',
    `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    INDEX idx_route_number (`route_number`),
    INDEX idx_route_status (`route_status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='线路表';

-- 站点表
CREATE TABLE IF NOT EXISTS `station` (
    `id` BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '站点ID',
    `station_name` VARCHAR(100) NOT NULL COMMENT '站点名称',
    `longitude` DECIMAL(10, 7) COMMENT '经度',
    `latitude` DECIMAL(10, 7) COMMENT '纬度',
    `address` VARCHAR(255) COMMENT '地址',
    `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    INDEX idx_station_name (`station_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='站点表';

-- 线路站点关联表
CREATE TABLE IF NOT EXISTS `route_station` (
    `id` BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '关联ID',
    `route_id` BIGINT NOT NULL COMMENT '线路ID',
    `station_id` BIGINT NOT NULL COMMENT '站点ID',
    `sequence_number` INT NOT NULL COMMENT '站点序号',
    `arrival_time_offset` INT COMMENT '到达时间偏移(分钟)',
    `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    FOREIGN KEY (`route_id`) REFERENCES `route`(`id`) ON DELETE CASCADE,
    FOREIGN KEY (`station_id`) REFERENCES `station`(`id`) ON DELETE CASCADE,
    UNIQUE KEY uk_route_station (`route_id`, `station_id`),
    INDEX idx_route_sequence (`route_id`, `sequence_number`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='线路站点关联表';

-- 票务类型表
CREATE TABLE IF NOT EXISTS `ticket_type` (
    `id` BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '票务类型ID',
    `type_name` VARCHAR(50) NOT NULL COMMENT '类型名称',
    `validity_type` VARCHAR(20) NOT NULL COMMENT '有效期类型:SINGLE-单次,DAILY-按天,MONTHLY-按月',
    `validity_duration` INT NOT NULL COMMENT '有效期时长(天数或次数)',
    `applicable_scope` VARCHAR(20) NOT NULL COMMENT '适用范围:SINGLE_ROUTE-单线路,ALL_ROUTES-全线路',
    `refundable` BOOLEAN NOT NULL DEFAULT TRUE COMMENT '是否可退',
    `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    INDEX idx_type_name (`type_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='票务类型表';

-- 票价规则表
CREATE TABLE IF NOT EXISTS `price_rule` (
    `id` BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '规则ID',
    `route_id` BIGINT COMMENT '线路ID(NULL表示全局规则)',
    `ticket_type_id` BIGINT NOT NULL COMMENT '票务类型ID',
    `identity_type` VARCHAR(20) NOT NULL COMMENT '用户身份类型',
    `price` DECIMAL(10, 2) NOT NULL COMMENT '价格(元)',
    `effective_date` DATE NOT NULL COMMENT '生效日期',
    `expiry_date` DATE COMMENT '失效日期',
    `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    FOREIGN KEY (`route_id`) REFERENCES `route`(`id`) ON DELETE CASCADE,
    FOREIGN KEY (`ticket_type_id`) REFERENCES `ticket_type`(`id`) ON DELETE CASCADE,
    INDEX idx_route_ticket_type (`route_id`, `ticket_type_id`),
    INDEX idx_effective_date (`effective_date`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='票价规则表';

-- 订单表
CREATE TABLE IF NOT EXISTS `orders` (
    `id` BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '订单ID',
    `user_id` BIGINT NOT NULL COMMENT '用户ID',
    `order_number` VARCHAR(50) NOT NULL UNIQUE COMMENT '订单号',
    `total_amount` DECIMAL(10, 2) NOT NULL COMMENT '总金额',
    `payment_status` VARCHAR(20) NOT NULL DEFAULT 'PENDING' COMMENT '支付状态:PENDING-待支付,PAID-已支付,CANCELLED-已取消,REFUNDED-已退款',
    `payment_time` TIMESTAMP NULL COMMENT '支付时间',
    `payment_method` VARCHAR(20) COMMENT '支付方式:WECHAT-微信,ALIPAY-支付宝,OTHER-其他',
    `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    FOREIGN KEY (`user_id`) REFERENCES `user`(`id`) ON DELETE CASCADE,
    INDEX idx_user_id (`user_id`),
    INDEX idx_order_number (`order_number`),
    INDEX idx_payment_status (`payment_status`),
    INDEX idx_created_at (`created_at`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订单表';

-- 车票表
CREATE TABLE IF NOT EXISTS `ticket` (
    `id` BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '车票ID',
    `order_id` BIGINT NOT NULL COMMENT '订单ID',
    `ticket_type_id` BIGINT NOT NULL COMMENT '票务类型ID',
    `route_id` BIGINT COMMENT '线路ID(NULL表示全线路票)',
    `ticket_code` VARCHAR(100) NOT NULL UNIQUE COMMENT '票据编号',
    `purchase_price` DECIMAL(10, 2) NOT NULL COMMENT '购买价格',
    `valid_from` TIMESTAMP NOT NULL COMMENT '生效时间',
    `valid_until` TIMESTAMP NOT NULL COMMENT '失效时间',
    `usage_status` VARCHAR(20) NOT NULL DEFAULT 'UNUSED' COMMENT '使用状态:UNUSED-未使用,USED-已使用,EXPIRED-已过期,REFUNDED-已退票',
    `used_time` TIMESTAMP NULL COMMENT '使用时间',
    `validator_id` BIGINT COMMENT '验票员ID',
    `qr_code_data` TEXT COMMENT '二维码数据',
    `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    FOREIGN KEY (`order_id`) REFERENCES `orders`(`id`) ON DELETE CASCADE,
    FOREIGN KEY (`ticket_type_id`) REFERENCES `ticket_type`(`id`),
    FOREIGN KEY (`route_id`) REFERENCES `route`(`id`),
    INDEX idx_ticket_code (`ticket_code`),
    INDEX idx_usage_status (`usage_status`),
    INDEX idx_valid_until (`valid_until`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='车票表';

-- 车辆表
CREATE TABLE IF NOT EXISTS `vehicle` (
    `id` BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '车辆ID',
    `license_plate` VARCHAR(20) NOT NULL UNIQUE COMMENT '车牌号',
    `vehicle_model` VARCHAR(50) COMMENT '车辆型号',
    `capacity` INT COMMENT '载客量',
    `current_route_id` BIGINT COMMENT '当前线路ID',
    `vehicle_status` VARCHAR(20) NOT NULL DEFAULT 'OPERATING' COMMENT '车辆状态:OPERATING-运营中,MAINTENANCE-维修,DECOMMISSIONED-停用',
    `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    FOREIGN KEY (`current_route_id`) REFERENCES `route`(`id`) ON DELETE SET NULL,
    INDEX idx_license_plate (`license_plate`),
    INDEX idx_vehicle_status (`vehicle_status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='车辆表';

-- 验票记录表
CREATE TABLE IF NOT EXISTS `validation_record` (
    `id` BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '记录ID',
    `ticket_id` BIGINT NOT NULL COMMENT '车票ID',
    `validator_id` BIGINT NOT NULL COMMENT '验票员ID',
    `route_id` BIGINT NOT NULL COMMENT '线路ID',
    `validation_time` TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '验票时间',
    `validation_result` VARCHAR(20) NOT NULL COMMENT '验证结果:SUCCESS-成功,FAILED-失败',
    `failure_reason` VARCHAR(255) COMMENT '失败原因',
    FOREIGN KEY (`ticket_id`) REFERENCES `ticket`(`id`),
    INDEX idx_ticket_id (`ticket_id`),
    INDEX idx_validation_time (`validation_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='验票记录表';

-- 退款记录表
CREATE TABLE IF NOT EXISTS `refund_record` (
    `id` BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '退款ID',
    `ticket_id` BIGINT NOT NULL COMMENT '车票ID',
    `user_id` BIGINT NOT NULL COMMENT '用户ID',
    `refund_amount` DECIMAL(10, 2) NOT NULL COMMENT '退款金额',
    `service_fee` DECIMAL(10, 2) DEFAULT 0 COMMENT '手续费',
    `refund_reason` VARCHAR(255) COMMENT '退款原因',
    `refund_status` VARCHAR(20) NOT NULL DEFAULT 'PROCESSING' COMMENT '退款状态:PROCESSING-处理中,COMPLETED-已完成,FAILED-失败',
    `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '申请时间',
    `completed_at` TIMESTAMP NULL COMMENT '完成时间',
    FOREIGN KEY (`ticket_id`) REFERENCES `ticket`(`id`),
    FOREIGN KEY (`user_id`) REFERENCES `user`(`id`),
    INDEX idx_ticket_id (`ticket_id`),
    INDEX idx_user_id (`user_id`),
    INDEX idx_refund_status (`refund_status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='退款记录表';

-- 插入初始数据

-- 插入票务类型
INSERT INTO `ticket_type` (`type_name`, `validity_type`, `validity_duration`, `applicable_scope`, `refundable`) VALUES
('单次票', 'SINGLE', 1, 'SINGLE_ROUTE', TRUE),
('日票', 'DAILY', 1, 'ALL_ROUTES', TRUE),
('周票', 'DAILY', 7, 'ALL_ROUTES', TRUE),
('月票', 'MONTHLY', 30, 'ALL_ROUTES', TRUE);

-- 插入示例线路
INSERT INTO `route` (`route_name`, `route_number`, `start_station`, `end_station`, `operation_start_time`, `operation_end_time`, `route_status`) VALUES
('101路', '101', '火车站', '科技园', '06:00:00', '22:00:00', 'OPERATING'),
('202路', '202', '市中心', '大学城', '06:30:00', '21:30:00', 'OPERATING'),
('303路', '303', '机场', '火车站', '05:00:00', '23:00:00', 'OPERATING');

-- 插入示例站点
INSERT INTO `station` (`station_name`, `longitude`, `latitude`, `address`) VALUES
('火车站', 116.407526, 39.904989, '北京市东城区火车站广场'),
('人民广场', 116.397428, 39.909202, '北京市东城区人民广场'),
('科技园', 116.327390, 39.983424, '北京市海淀区科技园区'),
('市中心', 116.407526, 39.904989, '北京市中心商业区'),
('大学城', 116.307390, 39.993424, '北京市海淀区大学城'),
('机场', 116.597390, 40.073424, '北京首都国际机场');

-- 插入线路站点关联
INSERT INTO `route_station` (`route_id`, `station_id`, `sequence_number`, `arrival_time_offset`) VALUES
(1, 1, 1, 0),
(1, 2, 2, 15),
(1, 3, 3, 35),
(2, 4, 1, 0),
(2, 2, 2, 20),
(2, 5, 3, 45),
(3, 6, 1, 0),
(3, 1, 2, 50);

-- 插入票价规则
INSERT INTO `price_rule` (`route_id`, `ticket_type_id`, `identity_type`, `price`, `effective_date`) VALUES
(1, 1, 'REGULAR', 2.00, '2025-01-01'),
(1, 1, 'STUDENT', 1.00, '2025-01-01'),
(1, 1, 'SENIOR', 0.00, '2025-01-01'),
(2, 1, 'REGULAR', 3.00, '2025-01-01'),
(2, 1, 'STUDENT', 1.50, '2025-01-01'),
(2, 1, 'SENIOR', 0.00, '2025-01-01'),
(3, 1, 'REGULAR', 25.00, '2025-01-01'),
(3, 1, 'STUDENT', 20.00, '2025-01-01'),
(3, 1, 'SENIOR', 20.00, '2025-01-01'),
(NULL, 2, 'REGULAR', 10.00, '2025-01-01'),
(NULL, 2, 'STUDENT', 5.00, '2025-01-01'),
(NULL, 2, 'SENIOR', 5.00, '2025-01-01'),
(NULL, 3, 'REGULAR', 60.00, '2025-01-01'),
(NULL, 3, 'STUDENT', 30.00, '2025-01-01'),
(NULL, 3, 'SENIOR', 30.00, '2025-01-01'),
(NULL, 4, 'REGULAR', 200.00, '2025-01-01'),
(NULL, 4, 'STUDENT', 100.00, '2025-01-01'),
(NULL, 4, 'SENIOR', 100.00, '2025-01-01');
