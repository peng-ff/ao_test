package com.bus.ticketing.dto.response;

import lombok.Data;

/**
 * 登录响应DTO
 */
@Data
public class LoginResponse {
    
    private Long userId;
    private String phone;
    private String name;
    private String identityType;
    private String token;
    
    public LoginResponse(Long userId, String phone, String name, String identityType, String token) {
        this.userId = userId;
        this.phone = phone;
        this.name = name;
        this.identityType = identityType;
        this.token = token;
    }
}
