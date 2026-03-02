package com.courseselection;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 选课系统主应用类
 */
@SpringBootApplication
@EnableTransactionManagement
public class CourseSelectionApplication {

    public static void main(String[] args) {
        SpringApplication.run(CourseSelectionApplication.class, args);
        System.out.println("\n==============================================");
        System.out.println("选课系统启动成功!");
        System.out.println("API文档地址: http://localhost:8080/swagger-ui.html");
        System.out.println("==============================================\n");
    }
}
