package com.shop.shopapp.services.implementations;

import com.shop.shopapp.controllers.ProductController;
import com.shop.shopapp.infrastructure.entities.Cart;
import com.shop.shopapp.infrastructure.entities.OrderItem;
import com.shop.shopapp.infrastructure.entities.Product;
import com.shop.shopapp.infrastructure.entities.User;
import com.shop.shopapp.infrastructure.repositories.CartRepository;
import com.shop.shopapp.infrastructure.repositories.OrderItemRepository;
import com.shop.shopapp.infrastructure.repositories.ProductRepository;
import com.shop.shopapp.infrastructure.repositories.UserRepository;
import com.shop.shopapp.security.UserContextAccessor;
import com.shop.shopapp.security.UserPrincipal;
import com.shop.shopapp.services.ShoppingCartService;
import com.shop.shopapp.services.dataTransferObjects.OrderItemDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Optional;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {

    private final UserRepository userRepository;
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final OrderItemRepository orderItemRepository;
    private final UserContextAccessor userContextAccessor;

    private Logger logger = LoggerFactory.getLogger(ShoppingCartServiceImpl.class);
    @Autowired
    public ShoppingCartServiceImpl(UserRepository userRepository,
                                   CartRepository cartRepository,
                                   ProductRepository productRepository,
                                   OrderItemRepository orderItemRepository,
                                   UserContextAccessor userContextAccessor) {
        this.userRepository = userRepository;
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
        this.orderItemRepository = orderItemRepository;
        this.userContextAccessor = userContextAccessor;
    }

    public Cart getUserCart(){
        User user = userContextAccessor.getCurrentUser();
        Optional<Cart> cart = cartRepository.findCartByUser(user.getId());
        Cart resultCart;
        if(!cart.isPresent()){
            Cart newCart = new Cart();
            User u = userRepository.findById(user.getId()).get();
            newCart.setUser(u);
            resultCart = cartRepository.save(newCart);
            resultCart.setItems(new ArrayList<>());
            u.setCart(resultCart);
            userRepository.save(u);
        } else{
            resultCart = cart.get();
        }
        return resultCart;
    }

    @Override
    public Iterable<OrderItemDTO> getCart() {
        Cart cart = getUserCart();

        Iterable<OrderItemDTO> result = OrderItemDTO.mapItemsIterable(cart.getItems());

        return result;
    }

    @Override
    public Iterable<OrderItemDTO> addToCart(long productId, int quantity) {
        Cart cart = getUserCart();

        Optional<Product> productQ = productRepository.findById(productId);
        if(!productQ.isPresent()){
            logger.info("returned 400, No Product with given id was found");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"No Product with given id was found");
        }

        Product product = productQ.get();

        Optional<OrderItem> cartItemQ = cart.getItems().stream().filter(p->{ return p.getProduct().getId() == productId; }).findFirst();
        if(cartItemQ.isPresent()){
            OrderItem cartItem = cartItemQ.get();
            if(quantity + cartItem.getQuantity() > product.getStock()){
                logger.info("returned 400, Not enough product in stock");
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Not enough product in stock");
            }

            cartItem.setQuantity(cartItem.getQuantity()+quantity);
            orderItemRepository.save(cartItem);
        }
        else{
            if(quantity > product.getStock()){
                logger.info("returned 400, Not enough product in stock");
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Not enough product in stock");
            }

            OrderItem item = new OrderItem(quantity,product);
            item.setCart(cart);
            orderItemRepository.save(item);

            cart.addItem(item);
        }

        cartRepository.save(cart);

        Iterable<OrderItemDTO> result = OrderItemDTO.mapItemsIterable(cart.getItems());

        return result;
    }

    @Override
    public Iterable<OrderItemDTO>  removeFromCart(long productId, int quantity) {
        Cart cart = getUserCart();

        Optional<OrderItem> cartItemQ = cart.getItems().stream().filter(p->{ return p.getProduct().getId() == productId; }).findFirst();

        if(!cartItemQ.isPresent()){
            logger.info("returned 400, No Product with given id was found in cart");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"No Product with given id was found in cart");
        }

        OrderItem cartItem = cartItemQ.get();

        if(quantity >= cartItem.getQuantity()){
            cart.removeItem(cartItem);
            orderItemRepository.delete(cartItem);
        }
        else{
            cartItem.setQuantity(cartItem.getQuantity()-quantity);
            orderItemRepository.save(cartItem);
        }

        cartRepository.save(cart);

        Iterable<OrderItemDTO> result = OrderItemDTO.mapItemsIterable(cart.getItems());

        return result;
    }

    @Override
    public void clearCart() {
        Cart cart = getUserCart();

        Iterator<OrderItem> iterator = cart.getItems().iterator();
        while(iterator.hasNext()){
            OrderItem o = iterator.next();
            orderItemRepository.delete(o);
        }

        cartRepository.save(cart);
    }


}
