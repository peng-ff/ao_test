package com.square.shopping.controller;

import com.square.shopping.dto.request.CreateOrderRequest;
import com.square.shopping.dto.response.ApiResponse;
import com.square.shopping.dto.response.OrderResponse;
import com.square.shopping.dto.response.PageResponse;
import com.square.shopping.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Order controller for order management
 */
@RestController
@RequestMapping("/api/orders")
@CrossOrigin(origins = "*")
public class OrderController {
    
    @Autowired
    private OrderService orderService;
    
    /**
     * Create order from cart
     */
    @PostMapping
    public ApiResponse<OrderResponse> createOrder(
            @RequestAttribute Long userId,
            @RequestBody CreateOrderRequest request) {
        OrderResponse response = orderService.createOrder(userId, request);
        return ApiResponse.success("Order created successfully", response);
    }
    
    /**
     * Get order by ID
     */
    @GetMapping("/{orderId}")
    public ApiResponse<OrderResponse> getOrder(
            @RequestAttribute Long userId,
            @PathVariable Long orderId) {
        OrderResponse response = orderService.getOrderById(orderId, userId);
        return ApiResponse.success(response);
    }
    
    /**
     * Get user orders
     */
    @GetMapping
    public ApiResponse<List<OrderResponse>> getUserOrders(
            @RequestAttribute Long userId,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int pageSize) {
        List<OrderResponse> orders = orderService.getUserOrders(userId, page, pageSize);
        return ApiResponse.success(orders);
    }
    
    /**
     * Cancel order
     */
    @PostMapping("/{orderId}/cancel")
    public ApiResponse<OrderResponse> cancelOrder(
            @RequestAttribute Long userId,
            @PathVariable Long orderId,
            @RequestParam(required = false) String reason) {
        OrderResponse response = orderService.cancelOrder(orderId, userId, reason);
        return ApiResponse.success("Order cancelled successfully", response);
    }
}
