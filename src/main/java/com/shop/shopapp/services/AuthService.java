package com.shop.shopapp.services;

import com.shop.shopapp.services.dataTransferObjects.UserDTO;

public interface AuthService {
    UserDTO createUser(UserDTO userDTO);
    String login(UserDTO userDTO);
}
