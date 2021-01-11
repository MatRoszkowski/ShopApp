package com.shop.shopapp.services;

import com.shop.shopapp.infrastructure.entities.User;
import com.shop.shopapp.services.dataTransferObjects.UserDTO;

import java.util.List;

public interface UserService {
    UserDTO getUserDtoById(Long id);
    User getUserById(Long id);
    void deleteUser(Long id);
    UserDTO updateUser(Long id, UserDTO userDTO);
    UserDTO getUserDtoByUsername(String username);
    List<UserDTO> searchUserDtoByUsername(String username);
}
