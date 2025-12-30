package com.bus.ticketing.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 错误码枚举
 */
@Getter
@AllArgsConstructor
public enum ErrorCode {
    
    // 通用错误
    SUCCESS("0000", "成功"),
    SYSTEM_ERROR("9999", "系统错误"),
    PARAM_ERROR("1001", "参数错误"),
    NOT_FOUND("1002", "资源不存在"),
    UNAUTHORIZED("1003", "未授权"),
    FORBIDDEN("1004", "无权限"),
    
    // 用户相关
    USER_NOT_FOUND("2001", "用户不存在"),
    USER_ALREADY_EXISTS("2002", "用户已存在"),
    USER_PHONE_INVALID("2003", "手机号格式错误"),
    USER_FROZEN("2004", "账户已冻结"),
    INVALID_CREDENTIALS("2005", "用户名或密码错误"),
    
    // 线路相关
    ROUTE_NOT_FOUND("3001", "线路不存在"),
    ROUTE_NOT_OPERATING("3002", "线路未运营"),
    ROUTE_ALREADY_EXISTS("3003", "线路已存在"),
    
    // 订单相关
    ORDER_NOT_FOUND("4001", "订单不存在"),
    ORDER_ALREADY_PAID("4002", "订单已支付"),
    ORDER_PAYMENT_FAILED("4003", "支付失败"),
    ORDER_CANCELLED("4004", "订单已取消"),
    
    // 车票相关
    TICKET_NOT_FOUND("5001", "车票不存在"),
    TICKET_ALREADY_USED("5002", "车票已使用"),
    TICKET_EXPIRED("5003", "车票已过期"),
    TICKET_ROUTE_MISMATCH("5004", "车票线路不匹配"),
    TICKET_NOT_REFUNDABLE("5005", "车票不可退"),
    TICKET_ALREADY_REFUNDED("5006", "车票已退票"),
    
    // 票价相关
    PRICE_RULE_NOT_FOUND("6001", "票价规则不存在"),
    TICKET_TYPE_NOT_FOUND("6002", "票务类型不存在");
    
    private final String code;
    private final String message;
}
