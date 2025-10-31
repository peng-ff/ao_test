package com.square.checkin.exception;

/**
 * 签到相关异常
 */
public class CheckInException extends BusinessException {
    
    public CheckInException(String message) {
        super(400, message);
    }
    
    public CheckInException(Integer code, String message) {
        super(code, message);
    }
    
    /**
     * 重复签到异常
     */
    public static CheckInException duplicateCheckIn() {
        return new CheckInException("今日已签到,请勿重复签到");
    }
    
    /**
     * 用户不存在异常
     */
    public static CheckInException userNotFound(Long userId) {
        return new CheckInException(404, "用户不存在: " + userId);
    }
    
    /**
     * 参数无效异常
     */
    public static CheckInException invalidParameter(String message) {
        return new CheckInException(message);
    }
}
