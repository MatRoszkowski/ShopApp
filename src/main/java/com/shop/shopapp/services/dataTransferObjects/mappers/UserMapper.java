package com.shop.shopapp.services.dataTransferObjects.mappers;

import com.shop.shopapp.infrastructure.entities.User;
import com.shop.shopapp.services.dataTransferObjects.UserDTO;

import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class UserMapper implements Mapper<User, UserDTO> {

    @Override
    public UserDTO map(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .address(user.getAddress())
                .phoneNumber(user.getPhoneNumber())
                .roles(user.getRoles().stream().map(r -> r.getRoleName()).collect(Collectors.toSet()))
                .build();
    }

}
