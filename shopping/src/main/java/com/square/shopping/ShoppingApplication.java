package com.square.shopping;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Shopping website main application
 */
@SpringBootApplication
@MapperScan("com.square.shopping.repository")
@EnableTransactionManagement
public class ShoppingApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(ShoppingApplication.class, args);
        System.out.println("==============================================");
        System.out.println("Shopping Website Started Successfully!");
        System.out.println("API URL: http://localhost:8080/api");
        System.out.println("==============================================");
    }
}
