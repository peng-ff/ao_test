package com.square.shopping.service;

import com.square.shopping.dto.request.AddToCartRequest;
import com.square.shopping.dto.request.UpdateCartItemRequest;
import com.square.shopping.dto.response.CartResponse;
import com.square.shopping.entity.Cart;
import com.square.shopping.entity.CartItem;
import com.square.shopping.entity.Product;
import com.square.shopping.exception.BusinessException;
import com.square.shopping.repository.CartItemRepository;
import com.square.shopping.repository.CartRepository;
import com.square.shopping.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Cart service for shopping cart management
 */
@Service
public class CartService {
    
    @Autowired
    private CartRepository cartRepository;
    
    @Autowired
    private CartItemRepository cartItemRepository;
    
    @Autowired
    private ProductRepository productRepository;
    
    /**
     * Get or create cart for user
     */
    @Transactional
    public Cart getOrCreateCart(Long userId) {
        Cart cart = cartRepository.findByUserId(userId);
        if (cart == null) {
            cart = new Cart();
            cart.setUserId(userId);
            cartRepository.insert(cart);
        }
        return cart;
    }
    
    /**
     * Add item to cart
     */
    @Transactional
    public CartResponse addToCart(Long userId, AddToCartRequest request) {
        Cart cart = getOrCreateCart(userId);
        
        // Get product
        Product product = productRepository.findById(request.getProductId());
        if (product == null) {
            throw new BusinessException("Product not found");
        }
        
        if (!product.getActive()) {
            throw new BusinessException("Product is not available");
        }
        
        if (product.getStockQuantity() < request.getQuantity()) {
            throw new BusinessException("Insufficient stock");
        }
        
        // Check if item already exists in cart
        CartItem existingItem = cartItemRepository.findByCartIdAndProductId(cart.getId(), request.getProductId());
        if (existingItem != null) {
            // Update quantity
            int newQuantity = existingItem.getQuantity() + request.getQuantity();
            if (product.getStockQuantity() < newQuantity) {
                throw new BusinessException("Insufficient stock");
            }
            existingItem.setQuantity(newQuantity);
            cartItemRepository.update(existingItem);
        } else {
            // Add new item
            CartItem cartItem = new CartItem();
            cartItem.setCartId(cart.getId());
            cartItem.setProductId(request.getProductId());
            cartItem.setQuantity(request.getQuantity());
            cartItem.setPrice(product.getPrice());
            cartItemRepository.insert(cartItem);
        }
        
        // Update cart timestamp
        cartRepository.updateTimestamp(cart.getId());
        
        return getCartDetails(userId);
    }
    
    /**
     * Update cart item quantity
     */
    @Transactional
    public CartResponse updateCartItem(Long userId, UpdateCartItemRequest request) {
        CartItem cartItem = cartItemRepository.findById(request.getCartItemId());
        if (cartItem == null) {
            throw new BusinessException("Cart item not found");
        }
        
        // Verify ownership
        Cart cart = cartRepository.findById(cartItem.getCartId());
        if (!cart.getUserId().equals(userId)) {
            throw new BusinessException("Unauthorized access");
        }
        
        // Check stock
        Product product = productRepository.findById(cartItem.getProductId());
        if (product.getStockQuantity() < request.getQuantity()) {
            throw new BusinessException("Insufficient stock");
        }
        
        cartItem.setQuantity(request.getQuantity());
        cartItemRepository.update(cartItem);
        
        return getCartDetails(userId);
    }
    
    /**
     * Remove item from cart
     */
    @Transactional
    public CartResponse removeFromCart(Long userId, Long cartItemId) {
        CartItem cartItem = cartItemRepository.findById(cartItemId);
        if (cartItem == null) {
            throw new BusinessException("Cart item not found");
        }
        
        // Verify ownership
        Cart cart = cartRepository.findById(cartItem.getCartId());
        if (!cart.getUserId().equals(userId)) {
            throw new BusinessException("Unauthorized access");
        }
        
        cartItemRepository.deleteById(cartItemId);
        
        return getCartDetails(userId);
    }
    
    /**
     * Get cart details with items
     */
    public CartResponse getCartDetails(Long userId) {
        Cart cart = getOrCreateCart(userId);
        List<CartItem> items = cartItemRepository.findByCartId(cart.getId());
        
        CartResponse response = new CartResponse();
        response.setCartId(cart.getId());
        
        List<CartResponse.CartItemDto> itemDtos = new ArrayList<>();
        BigDecimal totalAmount = BigDecimal.ZERO;
        int totalItems = 0;
        
        for (CartItem item : items) {
            Product product = productRepository.findById(item.getProductId());
            if (product != null && product.getActive()) {
                CartResponse.CartItemDto dto = new CartResponse.CartItemDto();
                dto.setCartItemId(item.getId());
                dto.setProductId(product.getId());
                dto.setProductName(product.getName());
                dto.setPrice(item.getPrice());
                dto.setQuantity(item.getQuantity());
                dto.setImageUrl(product.getImageUrl());
                
                BigDecimal subtotal = item.getPrice().multiply(new BigDecimal(item.getQuantity()));
                dto.setSubtotal(subtotal);
                
                itemDtos.add(dto);
                totalAmount = totalAmount.add(subtotal);
                totalItems += item.getQuantity();
            }
        }
        
        response.setItems(itemDtos);
        response.setTotalAmount(totalAmount);
        response.setTotalItems(totalItems);
        
        return response;
    }
    
    /**
     * Clear cart
     */
    @Transactional
    public void clearCart(Long userId) {
        Cart cart = cartRepository.findByUserId(userId);
        if (cart != null) {
            cartItemRepository.deleteByCartId(cart.getId());
        }
    }
}
