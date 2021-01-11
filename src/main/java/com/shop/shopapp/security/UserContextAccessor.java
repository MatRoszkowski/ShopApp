package com.shop.shopapp.security;

import com.shop.shopapp.infrastructure.entities.User;
import com.shop.shopapp.infrastructure.repositories.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class UserContextAccessor implements ContextAccessor {
    private final UserRepository userRepository;

    public UserContextAccessor(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserPrincipal getCurrentUserPrincipalContext(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserPrincipal user = null;
        if (principal instanceof UserDetails) {
            user = (UserPrincipal) principal;
        } else {
            String username = principal.toString();
        }
        return user;
    }
    public User getCurrentUser(){
        return userRepository.findById(getCurrentUserPrincipalContext().getId()).get();
    }
}
