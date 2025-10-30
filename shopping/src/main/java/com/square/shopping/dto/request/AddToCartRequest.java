package com.square.shopping.dto.request;

/**
 * Add item to cart request
 */
public class AddToCartRequest {
    private Long productId;
    private Integer quantity;

    // Getters and Setters
    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
