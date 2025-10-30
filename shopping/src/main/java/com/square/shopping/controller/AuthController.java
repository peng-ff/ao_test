package com.square.shopping.controller;

import com.square.shopping.dto.request.LoginRequest;
import com.square.shopping.dto.request.RegisterRequest;
import com.square.shopping.dto.response.ApiResponse;
import com.square.shopping.dto.response.AuthResponse;
import com.square.shopping.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Authentication controller
 */
@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {
    
    @Autowired
    private UserService userService;
    
    /**
     * Register new user
     */
    @PostMapping("/register")
    public ApiResponse<AuthResponse> register(@RequestBody RegisterRequest request) {
        AuthResponse response = userService.register(request);
        return ApiResponse.success("Registration successful", response);
    }
    
    /**
     * Login user
     */
    @PostMapping("/login")
    public ApiResponse<AuthResponse> login(@RequestBody LoginRequest request) {
        AuthResponse response = userService.login(request);
        return ApiResponse.success("Login successful", response);
    }
}
