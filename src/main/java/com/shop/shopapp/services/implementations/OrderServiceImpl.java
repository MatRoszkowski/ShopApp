package com.shop.shopapp.services.implementations;

import com.shop.shopapp.controllers.UserController;
import com.shop.shopapp.infrastructure.entities.*;
import com.shop.shopapp.infrastructure.repositories.OrderItemRepository;
import com.shop.shopapp.infrastructure.repositories.OrderRepository;
import com.shop.shopapp.infrastructure.repositories.ProductRepository;
import com.shop.shopapp.security.ContextAccessor;
import com.shop.shopapp.security.UserContextAccessor;
import com.shop.shopapp.services.EmailService;
import com.shop.shopapp.services.OrderService;
import com.shop.shopapp.services.ShoppingCartService;
import com.shop.shopapp.services.dataTransferObjects.EmailDTO;
import com.shop.shopapp.services.dataTransferObjects.OrderDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;

@Service
public class OrderServiceImpl implements OrderService {
    private final ShoppingCartService cartService;
    private final OrderItemRepository orderItemRepository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final ContextAccessor userContextAccessor;
    private final EmailService emailService;

    private Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);
    public OrderServiceImpl(ShoppingCartService cartService,
                            OrderItemRepository orderItemRepository,
                            OrderRepository orderRepository,
                            ProductRepository productRepository,
                            ContextAccessor userContextAccessor,
                            EmailService emailService) {
        this.cartService = cartService;
        this.orderItemRepository = orderItemRepository;
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.userContextAccessor = userContextAccessor;
        this.emailService = emailService;
    }

    @Override
    public OrderDTO placeOrder() {
        Cart c = cartService.getUserCart();
        User user = userContextAccessor.getCurrentUser();

        Iterator<OrderItem> itemIterator = c.getItems().iterator();
        while(itemIterator.hasNext()){
            OrderItem orderItem = itemIterator.next();
            if(orderItem.getQuantity() > productRepository.findById(orderItem.getProduct().getId()).get().getStock()){
                logger.info("Returned 400, Not enough product in stock");
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Not enough product in stock");
            }
        }

        Order order = new Order();
        order.setDateOfOrder(LocalDateTime.now());
        order.setUser(user);
        order = orderRepository.save(order);

        order.setItems(new ArrayList<OrderItem>());
        itemIterator = c.getItems().iterator();
        while(itemIterator.hasNext()){
            OrderItem orderItem = itemIterator.next();
            orderItem.setCart(null);
            orderItem.setOrder(order);
            orderItem = orderItemRepository.save(orderItem);

            Product product = orderItem.getProduct();
            product.setStock(product.getStock() - orderItem.getQuantity());
            productRepository.save(product);

            order.getItems().add(orderItem);
        }

        OrderDTO result = OrderDTO.mapItem(order);

        String emailMessage = "Hello " + (String)(user.getFirstName() != null ? user.getFirstName() : user.getUsername()) +
                "\n\n The Order Number #" + result.getId() + " was placed\n\nItems:\n";
        int i = 1;
        BigDecimal totalCost = BigDecimal.valueOf(0);
        for(OrderItem o : order.getItems()){
            BigDecimal cost = o.getProduct().getPrice().multiply(BigDecimal.valueOf(o.getQuantity()));
            totalCost = totalCost.add(cost);
            emailMessage += (String)(i++ + ". " + o.getQuantity() + "x " + o.getProduct().getName() + " "
                    + cost.doubleValue() + "\n");
        }
        emailMessage += "Total: "+totalCost.doubleValue() + "\n";
        emailMessage += "\n\nThank you for your purchase";
        EmailDTO email = new EmailDTO();
        email.setMessage(emailMessage);
        email.setSubject("ShopApp - Order #" + result.getId());
        email.setUser(user);
        emailService.sendEmail(user, email);

        logger.info("Order #"+order.getId()+" placed by #"+user.getId()+" "+user.getUsername());

        return result;
    }

    @Override
    public Iterable<OrderDTO> findAllUserOrders() {
        User user = userContextAccessor.getCurrentUser();

        Iterable<Order> orders = orderRepository.findOrdersByUser(user.getId());

        Iterable<OrderDTO> result = OrderDTO.mapItemsIterable(orders);

        return result;
    }

    @Override
    public Iterable<OrderDTO> findAllOrders() {
        Iterable<Order> orders = orderRepository.findAll();

        Iterable<OrderDTO> result = OrderDTO.mapItemsIterable(orders);

        return result;
    }
}
