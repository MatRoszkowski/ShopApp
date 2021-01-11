package com.shop.shopapp.services;

import com.shop.shopapp.infrastructure.entities.Order;
import com.shop.shopapp.services.dataTransferObjects.OrderDTO;

public interface OrderService {
    OrderDTO placeOrder();
    Iterable<OrderDTO> findAllUserOrders();
    Iterable<OrderDTO> findAllOrders();
}
