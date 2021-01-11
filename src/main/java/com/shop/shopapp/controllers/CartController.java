package com.shop.shopapp.controllers;

import com.shop.shopapp.infrastructure.entities.Product;
import com.shop.shopapp.services.ShoppingCartService;
import com.shop.shopapp.services.dataTransferObjects.OrderItemDTO;
import com.shop.shopapp.services.dataTransferObjects.requests.CartModRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/myCart")
@CrossOrigin(origins = "*")
public class CartController {
    private final ShoppingCartService cartService;

    @Autowired
    public CartController(ShoppingCartService cartService) {
        this.cartService = cartService;
    }

    private Logger logger = LoggerFactory.getLogger(CartController.class);

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/all")
    public Iterable<OrderItemDTO> GetCartItems(){
        logger.info("/myCart/all called");
        Iterable<OrderItemDTO> result = cartService.getCart();
        logger.info("/myCart/all call completed successfully");

        return result;
    }
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/add")
    public Iterable<OrderItemDTO> AddToCart(@RequestBody CartModRequest request){
        logger.info("/myCart/add called");
        Iterable<OrderItemDTO> result = cartService.addToCart(request.getProductId(),request.getQuantity());
        logger.info("/myCart/add call completed successfully");

        return result;
    }

    @PreAuthorize("isAuthenticated()")
    @PutMapping("/remove")
    public Iterable<OrderItemDTO> RemoveFromCart(@RequestBody CartModRequest request){
        logger.info("/myCart/remove called");
        Iterable<OrderItemDTO> result = cartService.removeFromCart(request.getProductId(),request.getQuantity());
        logger.info("/myCart/remove call completed successfully");

        return result;
    }

    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/clear")
    public void ClearCart(){
        logger.info("/myCart/clear called");
        cartService.clearCart();
        logger.info("/myCart/clear call completed successfully");
    }
}
