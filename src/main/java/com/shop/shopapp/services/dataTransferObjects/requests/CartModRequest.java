package com.shop.shopapp.services.dataTransferObjects.requests;

public class CartModRequest {
    private long productId;
    private int quantity;

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public long getProductId() {
        return productId;
    }

    public int getQuantity() {
        return quantity;
    }
}
