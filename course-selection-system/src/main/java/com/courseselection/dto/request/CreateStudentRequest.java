package com.courseselection.dto.request;

import lombok.Data;

import javax.validation.constraints.*;

/**
 * 创建学生请求
 */
@Data
public class CreateStudentRequest {

    @NotBlank(message = "学号不能为空")
    @Size(max = 20, message = "学号长度不能超过20")
    private String studentNumber;

    @NotBlank(message = "姓名不能为空")
    @Size(max = 50, message = "姓名长度不能超过50")
    private String name;

    @Email(message = "邮箱格式不正确")
    @Size(max = 100, message = "邮箱长度不能超过100")
    private String email;

    @NotBlank(message = "密码不能为空")
    private String password;

    @NotBlank(message = "专业不能为空")
    @Size(max = 50, message = "专业长度不能超过50")
    private String major;

    @NotNull(message = "年级不能为空")
    private Integer grade;
}
