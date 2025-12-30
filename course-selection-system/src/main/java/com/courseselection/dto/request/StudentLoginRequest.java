package com.courseselection.dto.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 学生登录请求
 */
@Data
public class StudentLoginRequest {

    @NotBlank(message = "学号不能为空")
    private String studentNumber;

    @NotBlank(message = "密码不能为空")
    private String password;
}
