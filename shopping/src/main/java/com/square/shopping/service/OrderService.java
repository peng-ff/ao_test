package com.square.shopping.service;

import com.square.shopping.dto.request.CreateOrderRequest;
import com.square.shopping.dto.response.OrderResponse;
import com.square.shopping.entity.*;
import com.square.shopping.exception.BusinessException;
import com.square.shopping.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Order service for order management
 */
@Service
public class OrderService {
    
    @Autowired
    private OrderRepository orderRepository;
    
    @Autowired
    private OrderItemRepository orderItemRepository;
    
    @Autowired
    private CartRepository cartRepository;
    
    @Autowired
    private CartItemRepository cartItemRepository;
    
    @Autowired
    private ProductRepository productRepository;
    
    @Autowired
    private AddressRepository addressRepository;
    
    @Autowired
    private ProductService productService;
    
    /**
     * Create order from cart
     */
    @Transactional
    public OrderResponse createOrder(Long userId, CreateOrderRequest request) {
        // Get cart
        Cart cart = cartRepository.findByUserId(userId);
        if (cart == null) {
            throw new BusinessException("Cart not found");
        }
        
        List<CartItem> cartItems = cartItemRepository.findByCartId(cart.getId());
        if (cartItems.isEmpty()) {
            throw new BusinessException("Cart is empty");
        }
        
        // Verify shipping address
        Address shippingAddress = addressRepository.findById(request.getShippingAddressId());
        if (shippingAddress == null || !shippingAddress.getUserId().equals(userId)) {
            throw new BusinessException("Invalid shipping address");
        }
        
        // Calculate order amounts
        BigDecimal totalAmount = BigDecimal.ZERO;
        List<OrderItem> orderItems = new ArrayList<>();
        
        for (CartItem cartItem : cartItems) {
            Product product = productRepository.findById(cartItem.getProductId());
            if (product == null || !product.getActive()) {
                throw new BusinessException("Product " + cartItem.getProductId() + " is not available");
            }
            
            // Decrease stock
            productService.decreaseStock(product.getId(), cartItem.getQuantity());
            
            // Create order item
            OrderItem orderItem = new OrderItem();
            orderItem.setProductId(product.getId());
            orderItem.setProductName(product.getName());
            orderItem.setProductSku(product.getSku());
            orderItem.setPrice(cartItem.getPrice());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setSubtotal(cartItem.getPrice().multiply(new BigDecimal(cartItem.getQuantity())));
            
            orderItems.add(orderItem);
            totalAmount = totalAmount.add(orderItem.getSubtotal());
        }
        
        // Calculate fees
        BigDecimal shippingFee = calculateShippingFee(totalAmount);
        BigDecimal discountAmount = BigDecimal.ZERO; // TODO: Apply coupon if provided
        BigDecimal finalAmount = totalAmount.add(shippingFee).subtract(discountAmount);
        
        // Create order
        Order order = new Order();
        order.setOrderNumber(generateOrderNumber());
        order.setUserId(userId);
        order.setTotalAmount(totalAmount);
        order.setShippingFee(shippingFee);
        order.setDiscountAmount(discountAmount);
        order.setFinalAmount(finalAmount);
        order.setStatus("PENDING");
        order.setShippingAddressId(request.getShippingAddressId());
        order.setPaymentMethod(request.getPaymentMethod());
        order.setPaymentStatus("PENDING");
        order.setOrderDate(LocalDateTime.now());
        
        orderRepository.insert(order);
        
        // Save order items
        for (OrderItem orderItem : orderItems) {
            orderItem.setOrderId(order.getId());
            orderItemRepository.insert(orderItem);
        }
        
        // Clear cart
        cartItemRepository.deleteByCartId(cart.getId());
        
        // Update sold count
        for (OrderItem orderItem : orderItems) {
            productRepository.increaseSoldCount(orderItem.getProductId(), orderItem.getQuantity());
        }
        
        return buildOrderResponse(order);
    }
    
    /**
     * Get order by ID
     */
    public OrderResponse getOrderById(Long orderId, Long userId) {
        Order order = orderRepository.findById(orderId);
        if (order == null) {
            throw new BusinessException("Order not found");
        }
        
        if (!order.getUserId().equals(userId)) {
            throw new BusinessException("Unauthorized access");
        }
        
        return buildOrderResponse(order);
    }
    
    /**
     * Get user orders with pagination
     */
    public List<OrderResponse> getUserOrders(Long userId, int page, int pageSize) {
        int offset = (page - 1) * pageSize;
        List<Order> orders = orderRepository.findByUserId(userId, pageSize, offset);
        
        List<OrderResponse> responses = new ArrayList<>();
        for (Order order : orders) {
            responses.add(buildOrderResponse(order));
        }
        return responses;
    }
    
    /**
     * Cancel order
     */
    @Transactional
    public OrderResponse cancelOrder(Long orderId, Long userId, String reason) {
        Order order = orderRepository.findById(orderId);
        if (order == null) {
            throw new BusinessException("Order not found");
        }
        
        if (!order.getUserId().equals(userId)) {
            throw new BusinessException("Unauthorized access");
        }
        
        if (!"PENDING".equals(order.getStatus()) && !"CONFIRMED".equals(order.getStatus())) {
            throw new BusinessException("Order cannot be cancelled");
        }
        
        // Restore stock
        List<OrderItem> items = orderItemRepository.findByOrderId(orderId);
        for (OrderItem item : items) {
            productService.increaseStock(item.getProductId(), item.getQuantity());
        }
        
        orderRepository.cancel(orderId, reason);
        
        return getOrderById(orderId, userId);
    }
    
    /**
     * Build order response
     */
    private OrderResponse buildOrderResponse(Order order) {
        OrderResponse response = new OrderResponse();
        response.setOrderId(order.getId());
        response.setOrderNumber(order.getOrderNumber());
        response.setTotalAmount(order.getTotalAmount());
        response.setShippingFee(order.getShippingFee());
        response.setDiscountAmount(order.getDiscountAmount());
        response.setFinalAmount(order.getFinalAmount());
        response.setStatus(order.getStatus());
        response.setPaymentMethod(order.getPaymentMethod());
        response.setPaymentStatus(order.getPaymentStatus());
        response.setOrderDate(order.getOrderDate());
        response.setPaidAt(order.getPaidAt());
        response.setShippedAt(order.getShippedAt());
        response.setDeliveredAt(order.getDeliveredAt());
        
        // Get shipping address
        Address address = addressRepository.findById(order.getShippingAddressId());
        if (address != null) {
            OrderResponse.AddressDto addressDto = new OrderResponse.AddressDto();
            addressDto.setRecipientName(address.getRecipientName());
            addressDto.setPhone(address.getPhone());
            addressDto.setFullAddress(String.format("%s, %s, %s, %s, %s", 
                address.getStreet(), address.getDistrict(), address.getCity(), 
                address.getProvince(), address.getCountry()));
            response.setShippingAddress(addressDto);
        }
        
        // Get order items
        List<OrderItem> items = orderItemRepository.findByOrderId(order.getId());
        List<OrderResponse.OrderItemDto> itemDtos = new ArrayList<>();
        for (OrderItem item : items) {
            OrderResponse.OrderItemDto dto = new OrderResponse.OrderItemDto();
            dto.setOrderItemId(item.getId());
            dto.setProductId(item.getProductId());
            dto.setProductName(item.getProductName());
            dto.setProductSku(item.getProductSku());
            dto.setPrice(item.getPrice());
            dto.setQuantity(item.getQuantity());
            dto.setSubtotal(item.getSubtotal());
            itemDtos.add(dto);
        }
        response.setItems(itemDtos);
        
        return response;
    }
    
    /**
     * Generate order number
     */
    private String generateOrderNumber() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        String timestamp = LocalDateTime.now().format(formatter);
        int random = new Random().nextInt(9000) + 1000;
        return "ORD" + timestamp + random;
    }
    
    /**
     * Calculate shipping fee
     */
    private BigDecimal calculateShippingFee(BigDecimal totalAmount) {
        // Free shipping for orders over 100
        if (totalAmount.compareTo(new BigDecimal("100")) >= 0) {
            return BigDecimal.ZERO;
        }
        return new BigDecimal("10.00");
    }
}
