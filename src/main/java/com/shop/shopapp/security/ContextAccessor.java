package com.shop.shopapp.security;

import com.shop.shopapp.infrastructure.entities.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public interface ContextAccessor {
    public UserPrincipal getCurrentUserPrincipalContext();
    public User getCurrentUser();
}
