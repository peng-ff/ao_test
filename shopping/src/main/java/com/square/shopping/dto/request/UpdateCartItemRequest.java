package com.square.shopping.dto.request;

/**
 * Update cart item quantity request
 */
public class UpdateCartItemRequest {
    private Long cartItemId;
    private Integer quantity;

    // Getters and Setters
    public Long getCartItemId() {
        return cartItemId;
    }

    public void setCartItemId(Long cartItemId) {
        this.cartItemId = cartItemId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
