package com.shop.shopapp.services;

import com.shop.shopapp.infrastructure.entities.Cart;
import com.shop.shopapp.infrastructure.entities.Product;
import com.shop.shopapp.services.dataTransferObjects.OrderItemDTO;
import org.springframework.stereotype.Service;

public interface ShoppingCartService {
    Cart getUserCart();
    Iterable<OrderItemDTO>  getCart();
    Iterable<OrderItemDTO>  addToCart(long productId, int quantity);
    Iterable<OrderItemDTO>  removeFromCart(long productId, int quantity);
    void clearCart();
}
