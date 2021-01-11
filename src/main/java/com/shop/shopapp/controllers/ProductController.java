package com.shop.shopapp.controllers;

import com.shop.shopapp.services.dataTransferObjects.requests.RestockRequest;
import com.shop.shopapp.infrastructure.entities.Product;
import com.shop.shopapp.services.implementations.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/product")
public class ProductController {
    private final ProductService productService;


    private Logger logger = LoggerFactory.getLogger(ProductController.class);
    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/all")
    public Iterable<Product> FindAllAvailable(){
        logger.info("/product/all called");
        Iterable<Product> result = productService.findAllAvailableProducts();
        logger.info("/product/all call completed successfully");

        return result;
    }

    @GetMapping("/get/{id}")
    public Product FindById(@PathVariable long id){
        logger.info("/product/get/"+id+" called");
        Product result = productService.findById(id);
        logger.info("/product/get/"+id+" call completed successfully");

        return result;
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/allProd")
    public Iterable<Product> FindAll(){
        logger.info("/product/allProd called");
        Iterable<Product> result = productService.getAllProducts();
        logger.info("/product/allProd call completed successfully");

        return result;
    }

    @GetMapping("/findByName")
    public Iterable<Product> FindProductByName(@RequestParam String query){
        logger.info("/product/findByName called");
        Iterable<Product> result = productService.findByName(query);
        logger.info("/product/findByName call completed successfully");

        return result;
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_STOREKEEPER')")
    @PostMapping("/addNew")
    public Product AddNewProduct(@RequestBody Product product){
        logger.info("/product/restock called");
        Product result = productService.addNewProduct(product);
        logger.info("/product/restock call completed successfully");

        return result;
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_STOREKEEPER')")
    @PutMapping("/restock")
    public Product Restock(@RequestBody RestockRequest request){
        logger.info("/product/restock called");
        Product result = productService.restockProduct(request.getProductId(),request.getQuantity());
        logger.info("/product/restock call completed successfully");

        return result;
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_STOREKEEPER')")
    @PutMapping("/edit")
    public Product EditProduct(@RequestBody Product product){
        logger.info("/product/edit called");
        Product result = productService.editProduct(product);
        logger.info("/product/edit call completed successfully");

        return result;
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_STOREKEEPER')")
    @DeleteMapping("/delete/{id}")
    public void DeleteProduct(@PathVariable long id){
        logger.info("/product/delete/"+id+" called");
        productService.deleteProduct(id);
        logger.info("/product/delete/"+id+" completed successfully");
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_STOREKEEPER')")
    @GetMapping("/myList")
    public Iterable<Product> GetAllUsersProducts(){
        logger.info("/product/mylist called");
        Iterable<Product> result = productService.getAllUsersProducts();
        logger.info("/product/mylist completed successfully");
        return result;
    }
}
