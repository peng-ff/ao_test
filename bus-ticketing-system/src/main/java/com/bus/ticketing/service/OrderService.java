package com.bus.ticketing.service;

import com.bus.ticketing.dto.request.CreateOrderRequest;
import com.bus.ticketing.entity.*;
import com.bus.ticketing.entity.enums.IdentityType;
import com.bus.ticketing.entity.enums.PaymentStatus;
import com.bus.ticketing.exception.BusinessException;
import com.bus.ticketing.exception.ErrorCode;
import com.bus.ticketing.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * 订单服务
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {
    
    private final OrderRepository orderRepository;
    private final TicketRepository ticketRepository;
    private final UserRepository userRepository;
    private final RouteRepository routeRepository;
    private final TicketTypeRepository ticketTypeRepository;
    private final PriceRuleRepository priceRuleRepository;
    private final TicketService ticketService;
    
    /**
     * 创建订单
     */
    @Transactional
    public Order createOrder(Long userId, CreateOrderRequest request) {
        log.info("创建订单: userId={}, request={}", userId, request);
        
        // 验证用户
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));
        
        // 验证线路
        Route route = routeRepository.findById(request.getRouteId())
                .orElseThrow(() -> new BusinessException(ErrorCode.ROUTE_NOT_FOUND));
        
        // 验证票务类型
        TicketType ticketType = ticketTypeRepository.findById(request.getTicketTypeId())
                .orElseThrow(() -> new BusinessException(ErrorCode.TICKET_TYPE_NOT_FOUND));
        
        // 计算票价
        BigDecimal unitPrice = calculatePrice(request.getRouteId(), request.getTicketTypeId(), user.getIdentityType());
        BigDecimal totalAmount = unitPrice.multiply(BigDecimal.valueOf(request.getQuantity()));
        
        // 创建订单
        Order order = new Order();
        order.setUserId(userId);
        order.setOrderNumber(generateOrderNumber());
        order.setTotalAmount(totalAmount);
        order.setPaymentStatus(PaymentStatus.PENDING);
        
        Order savedOrder = orderRepository.save(order);
        
        // 创建车票(暂时不生成,等支付成功后生成)
        log.info("订单创建成功: orderId={}, orderNumber={}", savedOrder.getId(), savedOrder.getOrderNumber());
        
        return savedOrder;
    }
    
    /**
     * 计算票价
     */
    private BigDecimal calculatePrice(Long routeId, Long ticketTypeId, IdentityType identityType) {
        PriceRule priceRule = priceRuleRepository.findApplicablePriceRule(
                routeId, ticketTypeId, identityType, LocalDate.now()
        ).orElseThrow(() -> new BusinessException(ErrorCode.PRICE_RULE_NOT_FOUND));
        
        return priceRule.getPrice();
    }
    
    /**
     * 生成订单号
     */
    private String generateOrderNumber() {
        return "ORD" + System.currentTimeMillis() + UUID.randomUUID().toString().substring(0, 6).toUpperCase();
    }
    
    /**
     * 模拟支付(简化版)
     */
    @Transactional
    public void mockPayment(Long orderId, Long userId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new BusinessException(ErrorCode.ORDER_NOT_FOUND));
        
        if (!order.getUserId().equals(userId)) {
            throw new BusinessException(ErrorCode.FORBIDDEN);
        }
        
        if (order.getPaymentStatus() == PaymentStatus.PAID) {
            throw new BusinessException(ErrorCode.ORDER_ALREADY_PAID);
        }
        
        // 更新订单状态
        order.setPaymentStatus(PaymentStatus.PAID);
        order.setPaymentTime(LocalDateTime.now());
        orderRepository.save(order);
        
        log.info("订单支付成功: orderId={}", orderId);
    }
    
    /**
     * 获取用户订单列表
     */
    public List<Order> getUserOrders(Long userId) {
        return orderRepository.findByUserIdOrderByCreatedAtDesc(userId);
    }
    
    /**
     * 获取订单详情
     */
    public Order getOrderById(Long orderId, Long userId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new BusinessException(ErrorCode.ORDER_NOT_FOUND));
        
        if (!order.getUserId().equals(userId)) {
            throw new BusinessException(ErrorCode.FORBIDDEN);
        }
        
        return order;
    }
}
