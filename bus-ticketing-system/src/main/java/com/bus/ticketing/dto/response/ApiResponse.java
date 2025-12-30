package com.bus.ticketing.dto.response;

import com.bus.ticketing.exception.ErrorCode;
import lombok.Data;

/**
 * API统一响应格式
 */
@Data
public class ApiResponse<T> {
    
    private String code;
    private String message;
    private T data;
    
    public ApiResponse() {
    }
    
    public ApiResponse(String code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }
    
    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(ErrorCode.SUCCESS.getCode(), ErrorCode.SUCCESS.getMessage(), data);
    }
    
    public static <T> ApiResponse<T> success() {
        return new ApiResponse<>(ErrorCode.SUCCESS.getCode(), ErrorCode.SUCCESS.getMessage(), null);
    }
    
    public static <T> ApiResponse<T> error(String code, String message) {
        return new ApiResponse<>(code, message, null);
    }
    
    public static <T> ApiResponse<T> error(String code, String message, T data) {
        return new ApiResponse<>(code, message, data);
    }
}
