package com.shop.shopapp.services.implementations;

import com.shop.shopapp.infrastructure.entities.OrderItem;
import com.shop.shopapp.infrastructure.entities.Product;
import com.shop.shopapp.infrastructure.entities.RoleName;
import com.shop.shopapp.infrastructure.entities.User;
import com.shop.shopapp.infrastructure.repositories.OrderItemRepository;
import com.shop.shopapp.infrastructure.repositories.ProductRepository;
import com.shop.shopapp.security.ContextAccessor;
import com.shop.shopapp.security.UserContextAccessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final OrderItemRepository orderItemRepository;
    private final ContextAccessor userContextAccessor;

    private Logger logger = LoggerFactory.getLogger(ProductService.class);
    @Autowired
    public ProductService(ProductRepository productRepository,
                          OrderItemRepository orderItemRepository,
                          ContextAccessor userContextAccessor) {
        this.productRepository = productRepository;
        this.orderItemRepository = orderItemRepository;
        this.userContextAccessor = userContextAccessor;
    }

    public Iterable<Product> findAllAvailableProducts(){
        Iterable<Product> result = productRepository.findAllAvailable();

        return result;
    }

    public Product findById(long id){
        Optional<Product> opProduct = productRepository.findById(id);

        if(!opProduct.isPresent()){
            logger.info("returned 404, Product with given id was not found");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product with given id was not found");
        }

        Product result = opProduct.get();

        if(!result.isAvailable()){
            logger.info("returned 404, Product with given id was not found");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product with given id was not found");
        }

        return result;
    }

    public Iterable<Product> findByName(String name){
        Iterable<Product> result = productRepository.findByName(name);

        return result;
    }

    public Product addNewProduct(Product product){
        User user = userContextAccessor.getCurrentUser();

        product.setUser(user);

        Product result = productRepository.save(product);

        return result;
    }

    public Product restockProduct(long id, int addedStock){
        Optional<Product> opProduct = productRepository.findById(id);

        if(!opProduct.isPresent()){
            logger.info("returned 404, Product not found");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Product not found");
        }

        Product result = opProduct.get();

        User user = userContextAccessor.getCurrentUser();

        if(result.getUser().getId() != user.getId() && !user.getRoles().stream().anyMatch(r -> r.getRoleName() == RoleName.ROLE_ADMIN)){
            logger.info("returned 401, Unauthorized access");
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Unauthorized access");
        }

        result.setStock(result.getStock() + addedStock);

        result = productRepository.save(result);

        return result;
    }

    public void deleteProduct(long id){
        Optional<Product> opProduct = productRepository.findById(id);

        if(!opProduct.isPresent()){
            logger.info("returned 404, No product with given id was found");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"No product with given id was found");
        }

        Product product = opProduct.get();

        User user = userContextAccessor.getCurrentUser();

        if(product.getUser().getId() != user.getId() && !user.getRoles().stream().anyMatch(r -> r.getRoleName() == RoleName.ROLE_ADMIN)){
            logger.info("returned 401, Unauthorized access");
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Unauthorized access");
        }

        List<OrderItem> orderItemList = orderItemRepository.findAllWithProductInCart(product.getId());
        Iterator<OrderItem> itemIterator = orderItemList.iterator();
        while(itemIterator.hasNext()){
            OrderItem o = itemIterator.next();
            orderItemRepository.delete(o);
        }

        product.setAvailable(false);

        productRepository.save(product);
    }

    public Product editProduct(Product product){
        Optional<Product> opProduct = productRepository.findById(product.getId());

        if(!opProduct.isPresent()){
            logger.info("returned 404, No product with given id was found");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"No product with given id was found");
        }

        Product productTracked = opProduct.get();

        User user = userContextAccessor.getCurrentUser();

        if(productTracked.getUser().getId() != user.getId() && !user.getRoles().stream().anyMatch(r -> r.getRoleName() == RoleName.ROLE_ADMIN)) {
            logger.info("returned 401, Unauthorized access");
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Unauthorized access");
        }

        product.setUser(productTracked.getUser());

        return productRepository.save(product);
    }

    public Iterable<Product> getAllProducts(){
        return productRepository.findAll();
    }

    public Iterable<Product> getAllUsersProducts() {
        User user = userContextAccessor.getCurrentUser();

        Iterable<Product> result = productRepository.findByUser(user.getId());

        return result;
    }
}
