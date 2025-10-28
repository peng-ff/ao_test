package com.square.checkin;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 用户签到系统主应用类
 */
@SpringBootApplication
@EnableTransactionManagement
@MapperScan("com.square.checkin.repository")
public class CheckInApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(CheckInApplication.class, args);
        System.out.println("========================================");
        System.out.println("用户签到系统启动成功！");
        System.out.println("服务端口: 8081");
        System.out.println("API文档: http://localhost:8081/api/checkin");
        System.out.println("========================================");
    }
}
