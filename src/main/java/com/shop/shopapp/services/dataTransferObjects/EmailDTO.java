package com.shop.shopapp.services.dataTransferObjects;

import com.shop.shopapp.infrastructure.entities.RoleName;
import com.shop.shopapp.infrastructure.entities.User;
import lombok.Builder;

import java.util.Set;

public class EmailDTO {
    private Long id;
    private String subject;
    private String message;
    private User user;

    public EmailDTO() {
    }
//    @Builder
//    public EmailDTO(Long id, String subject, String message, User user) {
//        this.id = id;
//        this.subject = subject;
//        this.message = message;
//        this.user = user;
//    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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
