package com.shop.shopapp.infrastructure.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity(name = "emails")
public class Email extends BaseEntity{
    @Column(nullable = false)
    private String subject;
    @Column(nullable = false)
    private String message;
    @ManyToOne
    @JoinColumn(name="user_id", nullable=false)
    private User user;

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
