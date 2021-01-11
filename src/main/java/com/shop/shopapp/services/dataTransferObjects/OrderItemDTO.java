package com.shop.shopapp.services.dataTransferObjects;

import com.shop.shopapp.infrastructure.entities.OrderItem;
import com.shop.shopapp.infrastructure.entities.Product;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class OrderItemDTO {
    private Product product;
    private int quantity;

    public OrderItemDTO(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public static OrderItemDTO mapItem(OrderItem item){
        return new OrderItemDTO(item.getProduct(),item.getQuantity());
    }

    public static Iterable<OrderItemDTO> mapItemsIterable(Iterable<OrderItem> orderItems){
        Iterator<OrderItem> iterator = orderItems.iterator();
        List<OrderItemDTO> result = new ArrayList<OrderItemDTO>();
        while(iterator.hasNext()){
            OrderItem c = iterator.next();
            result.add(mapItem(c));
        }
        return result;
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
