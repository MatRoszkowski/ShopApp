package com.shop.shopapp.infrastructure.repositories;

import com.shop.shopapp.infrastructure.entities.Cart;
import com.shop.shopapp.infrastructure.entities.Product;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepository extends CrudRepository<Cart,Long> {
    @Query("SELECT c FROM carts c WHERE c.user.id = :userId")
    Optional<Cart> findCartByUser(@Param("userId") long id);
}
