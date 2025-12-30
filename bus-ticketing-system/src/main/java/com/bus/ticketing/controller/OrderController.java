package com.bus.ticketing.controller;

import com.bus.ticketing.dto.request.CreateOrderRequest;
import com.bus.ticketing.dto.response.ApiResponse;
import com.bus.ticketing.entity.Order;
import com.bus.ticketing.service.OrderService;
import com.bus.ticketing.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 订单控制器
 */
@Slf4j
@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {
    
    private final OrderService orderService;
    private final JwtUtil jwtUtil;
    
    /**
     * 创建订单
     */
    @PostMapping
    public ApiResponse<Order> createOrder(
            @RequestHeader("Authorization") String token,
            @Valid @RequestBody CreateOrderRequest request) {
        Long userId = getUserIdFromToken(token);
        Order order = orderService.createOrder(userId, request);
        return ApiResponse.success(order);
    }
    
    /**
     * 获取我的订单列表
     */
    @GetMapping("/my")
    public ApiResponse<List<Order>> getMyOrders(@RequestHeader("Authorization") String token) {
        Long userId = getUserIdFromToken(token);
        List<Order> orders = orderService.getUserOrders(userId);
        return ApiResponse.success(orders);
    }
    
    /**
     * 获取订单详情
     */
    @GetMapping("/{orderId}")
    public ApiResponse<Order> getOrderDetail(
            @RequestHeader("Authorization") String token,
            @PathVariable Long orderId) {
        Long userId = getUserIdFromToken(token);
        Order order = orderService.getOrderById(orderId, userId);
        return ApiResponse.success(order);
    }
    
    /**
     * 模拟支付订单
     */
    @PostMapping("/{orderId}/pay")
    public ApiResponse<Void> payOrder(
            @RequestHeader("Authorization") String token,
            @PathVariable Long orderId) {
        Long userId = getUserIdFromToken(token);
        orderService.mockPayment(orderId, userId);
        return ApiResponse.success();
    }
    
    /**
     * 从Token中获取用户ID
     */
    private Long getUserIdFromToken(String token) {
        // 移除 "Bearer " 前缀
        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
        return jwtUtil.getUserIdFromToken(token);
    }
}
