-- 选课系统数据库初始化脚本

-- 创建数据库
CREATE DATABASE IF NOT EXISTS course_selection_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE course_selection_db;

-- 学生表
CREATE TABLE IF NOT EXISTS student (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
    student_number VARCHAR(20) NOT NULL UNIQUE COMMENT '学号',
    name VARCHAR(50) NOT NULL COMMENT '姓名',
    email VARCHAR(100) COMMENT '邮箱',
    password VARCHAR(255) NOT NULL COMMENT '密码(BCrypt加密)',
    major VARCHAR(50) NOT NULL COMMENT '专业',
    grade INT NOT NULL COMMENT '年级',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    INDEX idx_email (email),
    INDEX idx_major_grade (major, grade)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='学生表';

-- 管理员表
CREATE TABLE IF NOT EXISTS admin (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
    username VARCHAR(20) NOT NULL UNIQUE COMMENT '用户名',
    name VARCHAR(50) NOT NULL COMMENT '姓名',
    password VARCHAR(255) NOT NULL COMMENT '密码(BCrypt加密)',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='管理员表';

-- 课程表
CREATE TABLE IF NOT EXISTS course (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
    course_code VARCHAR(20) NOT NULL UNIQUE COMMENT '课程代码',
    course_name VARCHAR(100) NOT NULL COMMENT '课程名称',
    description TEXT COMMENT '课程描述',
    credits INT NOT NULL COMMENT '学分',
    capacity INT NOT NULL COMMENT '课程容量',
    selected_count INT NOT NULL DEFAULT 0 COMMENT '已选人数',
    teacher VARCHAR(50) NOT NULL COMMENT '授课教师',
    semester VARCHAR(20) NOT NULL COMMENT '学期',
    status VARCHAR(20) NOT NULL DEFAULT 'OPEN' COMMENT '状态: OPEN-开放选课, CLOSED-关闭选课, ENDED-已结束',
    version INT NOT NULL DEFAULT 0 COMMENT '版本号(乐观锁)',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    INDEX idx_semester (semester),
    INDEX idx_status (status),
    INDEX idx_teacher (teacher),
    CONSTRAINT chk_capacity CHECK (capacity > 0),
    CONSTRAINT chk_credits CHECK (credits > 0),
    CONSTRAINT chk_selected_count CHECK (selected_count >= 0 AND selected_count <= capacity)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='课程表';

-- 课程时间表
CREATE TABLE IF NOT EXISTS course_schedule (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
    course_id BIGINT NOT NULL COMMENT '课程ID',
    day_of_week VARCHAR(10) NOT NULL COMMENT '星期: MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY',
    start_period INT NOT NULL COMMENT '开始节次(1-12)',
    end_period INT NOT NULL COMMENT '结束节次(1-12)',
    location VARCHAR(50) COMMENT '上课地点',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    INDEX idx_course_id (course_id),
    FOREIGN KEY (course_id) REFERENCES course(id) ON DELETE CASCADE,
    CONSTRAINT chk_period CHECK (start_period >= 1 AND start_period <= 12 AND end_period >= 1 AND end_period <= 12 AND start_period <= end_period)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='课程时间表';

-- 选课记录表
CREATE TABLE IF NOT EXISTS course_selection (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
    student_id BIGINT NOT NULL COMMENT '学生ID',
    course_id BIGINT NOT NULL COMMENT '课程ID',
    selection_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '选课时间',
    status VARCHAR(20) NOT NULL DEFAULT 'SELECTED' COMMENT '状态: SELECTED-已选中, DROPPED-已退课',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    INDEX idx_student_course (student_id, course_id),
    INDEX idx_course_status (course_id, status),
    INDEX idx_status (status),
    FOREIGN KEY (student_id) REFERENCES student(id) ON DELETE CASCADE,
    FOREIGN KEY (course_id) REFERENCES course(id) ON DELETE CASCADE,
    UNIQUE KEY uk_student_course_active (student_id, course_id, status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='选课记录表';

-- 已完成课程表
CREATE TABLE IF NOT EXISTS completed_course (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
    student_id BIGINT NOT NULL COMMENT '学生ID',
    course_id BIGINT NOT NULL COMMENT '课程ID',
    semester VARCHAR(20) NOT NULL COMMENT '完成学期',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    INDEX idx_student_id (student_id),
    INDEX idx_course_id (course_id),
    FOREIGN KEY (student_id) REFERENCES student(id) ON DELETE CASCADE,
    FOREIGN KEY (course_id) REFERENCES course(id) ON DELETE CASCADE,
    UNIQUE KEY uk_student_course (student_id, course_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='已完成课程表';

-- 课程前置关系表
CREATE TABLE IF NOT EXISTS course_prerequisite (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
    course_id BIGINT NOT NULL COMMENT '课程ID',
    prerequisite_course_id BIGINT NOT NULL COMMENT '前置课程ID',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    INDEX idx_course_id (course_id),
    FOREIGN KEY (course_id) REFERENCES course(id) ON DELETE CASCADE,
    FOREIGN KEY (prerequisite_course_id) REFERENCES course(id) ON DELETE CASCADE,
    UNIQUE KEY uk_course_prerequisite (course_id, prerequisite_course_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='课程前置关系表';

-- 插入初始管理员账户
-- 默认密码: admin123 (BCrypt加密后的值)
INSERT INTO admin (username, name, password) VALUES 
('admin', '系统管理员', '$2a$10$X5wFuJTofsJK8GwXw3U7hOzD1LzG5KIQpjV7J/hZ8xLz7.JQN4ZKO');

-- 插入示例学生账户
-- 默认密码: student123
INSERT INTO student (student_number, name, email, password, major, grade) VALUES 
('2021001', '张三', 'zhangsan@example.com', '$2a$10$X5wFuJTofsJK8GwXw3U7hOzD1LzG5KIQpjV7J/hZ8xLz7.JQN4ZKO', '计算机科学与技术', 2021),
('2021002', '李四', 'lisi@example.com', '$2a$10$X5wFuJTofsJK8GwXw3U7hOzD1LzG5KIQpjV7J/hZ8xLz7.JQN4ZKO', '软件工程', 2021),
('2022001', '王五', 'wangwu@example.com', '$2a$10$X5wFuJTofsJK8GwXw3U7hOzD1LzG5KIQpjV7J/hZ8xLz7.JQN4ZKO', '计算机科学与技术', 2022);

-- 插入示例课程数据
INSERT INTO course (course_code, course_name, description, credits, capacity, teacher, semester, status) VALUES 
('CS101', '数据结构', '学习各种基本数据结构及其算法', 4, 60, '刘教授', '2024-2025-1', 'OPEN'),
('CS102', '算法设计与分析', '学习常见算法设计方法和复杂度分析', 4, 50, '陈教授', '2024-2025-1', 'OPEN'),
('CS201', '数据库系统', '关系型数据库理论与实践', 3, 55, '王教授', '2024-2025-1', 'OPEN'),
('CS202', '操作系统', '操作系统原理和实现', 4, 50, '李教授', '2024-2025-1', 'OPEN'),
('MATH101', '高等数学A', '微积分基础', 5, 80, '张教授', '2024-2025-1', 'OPEN');

-- 插入课程时间表
INSERT INTO course_schedule (course_id, day_of_week, start_period, end_period, location) VALUES 
(1, 'MONDAY', 1, 2, '教学楼A101'),
(1, 'WEDNESDAY', 3, 4, '教学楼A101'),
(2, 'TUESDAY', 1, 2, '教学楼A102'),
(2, 'THURSDAY', 3, 4, '教学楼A102'),
(3, 'MONDAY', 5, 6, '教学楼B201'),
(3, 'FRIDAY', 1, 2, '教学楼B201'),
(4, 'TUESDAY', 5, 6, '教学楼B202'),
(4, 'THURSDAY', 7, 8, '教学楼B202'),
(5, 'WEDNESDAY', 1, 2, '教学楼C301'),
(5, 'FRIDAY', 3, 4, '教学楼C301');

-- 插入课程前置关系 (算法设计需要先学数据结构)
INSERT INTO course_prerequisite (course_id, prerequisite_course_id) VALUES 
(2, 1);

-- 插入已完成课程示例 (张三已完成数据结构)
INSERT INTO completed_course (student_id, course_id, semester) VALUES 
(1, 1, '2023-2024-2');
