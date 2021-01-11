package com.shop.shopapp.infrastructure.entities;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity(name = "orders")
public class Order extends BaseEntity {
    @OneToMany(mappedBy = "order", fetch = FetchType.EAGER)
    private List<OrderItem> items;
    private LocalDateTime dateOfOrder;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setItems(List<OrderItem> items) {
        this.items = items;
    }

    public void setDateOfOrder(LocalDateTime dateOfOrder) {
        this.dateOfOrder = dateOfOrder;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public LocalDateTime getDateOfOrder() {
        return dateOfOrder;
    }
}
