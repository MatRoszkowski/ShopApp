package com.shop.shopapp.infrastructure.repositories;

import com.shop.shopapp.infrastructure.entities.Cart;
import com.shop.shopapp.infrastructure.entities.Order;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepository extends CrudRepository<Order,Long> {
    @Query("SELECT o FROM orders o WHERE o.user.id = :userId")
    List<Order> findOrdersByUser(@Param("userId") long id);
}
