package com.courseselection.controller;

import com.courseselection.dto.request.AdminLoginRequest;
import com.courseselection.dto.request.StudentLoginRequest;
import com.courseselection.dto.response.ApiResponse;
import com.courseselection.dto.response.LoginResponse;
import com.courseselection.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 认证控制器
 */
@RestController
@RequestMapping("/api/auth")
@Tag(name = "认证接口", description = "用户登录相关接口")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/student/login")
    @Operation(summary = "学生登录")
    public ApiResponse<LoginResponse> studentLogin(@Valid @RequestBody StudentLoginRequest request) {
        LoginResponse response = authService.studentLogin(request);
        return ApiResponse.success(response);
    }

    @PostMapping("/admin/login")
    @Operation(summary = "管理员登录")
    public ApiResponse<LoginResponse> adminLogin(@Valid @RequestBody AdminLoginRequest request) {
        LoginResponse response = authService.adminLogin(request);
        return ApiResponse.success(response);
    }
}
