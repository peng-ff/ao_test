package com.square.shopping.controller;

import com.square.shopping.dto.request.AddToCartRequest;
import com.square.shopping.dto.request.UpdateCartItemRequest;
import com.square.shopping.dto.response.ApiResponse;
import com.square.shopping.dto.response.CartResponse;
import com.square.shopping.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Cart controller for shopping cart management
 */
@RestController
@RequestMapping("/api/cart")
@CrossOrigin(origins = "*")
public class CartController {
    
    @Autowired
    private CartService cartService;
    
    /**
     * Get cart details
     */
    @GetMapping
    public ApiResponse<CartResponse> getCart(@RequestAttribute Long userId) {
        CartResponse response = cartService.getCartDetails(userId);
        return ApiResponse.success(response);
    }
    
    /**
     * Add item to cart
     */
    @PostMapping("/items")
    public ApiResponse<CartResponse> addToCart(
            @RequestAttribute Long userId,
            @RequestBody AddToCartRequest request) {
        CartResponse response = cartService.addToCart(userId, request);
        return ApiResponse.success("Item added to cart", response);
    }
    
    /**
     * Update cart item quantity
     */
    @PutMapping("/items")
    public ApiResponse<CartResponse> updateCartItem(
            @RequestAttribute Long userId,
            @RequestBody UpdateCartItemRequest request) {
        CartResponse response = cartService.updateCartItem(userId, request);
        return ApiResponse.success("Cart updated", response);
    }
    
    /**
     * Remove item from cart
     */
    @DeleteMapping("/items/{cartItemId}")
    public ApiResponse<CartResponse> removeFromCart(
            @RequestAttribute Long userId,
            @PathVariable Long cartItemId) {
        CartResponse response = cartService.removeFromCart(userId, cartItemId);
        return ApiResponse.success("Item removed from cart", response);
    }
    
    /**
     * Clear cart
     */
    @DeleteMapping
    public ApiResponse<Void> clearCart(@RequestAttribute Long userId) {
        cartService.clearCart(userId);
        return ApiResponse.success("Cart cleared", null);
    }
}
