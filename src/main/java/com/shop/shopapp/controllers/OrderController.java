package com.shop.shopapp.controllers;

import com.shop.shopapp.services.OrderService;
import com.shop.shopapp.services.dataTransferObjects.OrderDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;

    private Logger logger = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/place")
    public OrderDTO PlaceOrder(){
        logger.info("/order/place called");

        OrderDTO result = orderService.placeOrder();

        logger.info("/order/place call completed successfully");
        return result;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/allOrders")
    public Iterable<OrderDTO> GetAllOrders(){
        logger.info("/order/allOrders called");

        Iterable<OrderDTO> result = orderService.findAllOrders();

        logger.info("/order/allOrders call completed successfully");
        return result;
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/all")
    public Iterable<OrderDTO> GetAllUserOrders(){
        logger.info("/order/all called");

        Iterable<OrderDTO> result = orderService.findAllUserOrders();

        logger.info("/order/all call completed successfully");
        return result;
    }
}
