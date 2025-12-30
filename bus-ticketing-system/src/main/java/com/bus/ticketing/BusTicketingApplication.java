package com.bus.ticketing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * 公交车售票系统启动类
 */
@SpringBootApplication
@EnableJpaAuditing
public class BusTicketingApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(BusTicketingApplication.class, args);
        System.out.println("========================================");
        System.out.println("公交车售票系统启动成功!");
        System.out.println("API地址: http://localhost:8088/api");
        System.out.println("========================================");
    }
}
