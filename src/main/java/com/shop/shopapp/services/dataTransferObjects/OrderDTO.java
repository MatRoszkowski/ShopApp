package com.shop.shopapp.services.dataTransferObjects;

import com.shop.shopapp.infrastructure.entities.Order;
import com.shop.shopapp.infrastructure.entities.OrderItem;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class OrderDTO {
    private long id;
    private Iterable<OrderItemDTO> items;
    private LocalDateTime dateOfOrder;

    public OrderDTO(Iterable<OrderItem> items, long id, LocalDateTime dateOfOrder){
        this.id = id;
        this.items = OrderItemDTO.mapItemsIterable(items);
        this.dateOfOrder = dateOfOrder;
    }

    public static OrderDTO mapItem(Order order){
        return new OrderDTO(order.getItems(), order.getId(), order.getDateOfOrder());
    }

    public static Iterable<OrderDTO> mapItemsIterable(Iterable<Order> order){
        Iterator<Order> iterator = order.iterator();
        List<OrderDTO> result = new ArrayList<OrderDTO>();
        while(iterator.hasNext()){
            Order c = iterator.next();
            result.add(mapItem(c));
        }
        return result;
    }

    public void setDateOfOrder(LocalDateTime dateOfOrder) {
        this.dateOfOrder = dateOfOrder;
    }

    public LocalDateTime getDateOfOrder() {
        return dateOfOrder;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setItems(Iterable<OrderItemDTO> items) {
        this.items = items;
    }

    public long getId() {
        return id;
    }

    public Iterable<OrderItemDTO> getItems() {
        return items;
    }
}
