package com.shop.shopapp.infrastructure.repositories;

import com.shop.shopapp.infrastructure.entities.OrderItem;
import com.shop.shopapp.infrastructure.entities.Product;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemRepository extends CrudRepository<OrderItem,Long> {
    @Query("SELECT o FROM order_items o WHERE o.product.id = :productId AND o.product.available = true AND o.cart IS NOT NULL AND o.order IS NULL")
    public List<OrderItem> findAllWithProductInCart(@Param("productId") long productId);

}
