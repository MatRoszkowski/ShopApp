package com.shop.shopapp.infrastructure.repositories;

import com.shop.shopapp.infrastructure.entities.OrderItem;
import com.shop.shopapp.infrastructure.entities.Product;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends CrudRepository<Product,Long> {

    @Query("SELECT p FROM products p WHERE UPPER(p.name) LIKE CONCAT('%',UPPER(:name),'%')")
    List<Product> findByName(@Param("name") String name);

    @Query("SELECT p FROM products p WHERE p.available = true")
    List<Product> findAllAvailable();

    @Query("SELECT p FROM products p WHERE p.available = true AND p.user.id = :user_id")
    List<Product> findByUser(@Param("user_id") long id);

}
