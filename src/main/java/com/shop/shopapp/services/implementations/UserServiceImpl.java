package com.shop.shopapp.services.implementations;

import com.shop.shopapp.infrastructure.entities.User;
import com.shop.shopapp.infrastructure.repositories.UserRepository;
import com.shop.shopapp.services.UserService;
import com.shop.shopapp.services.dataTransferObjects.UserDTO;
import com.shop.shopapp.services.dataTransferObjects.mappers.UserMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public UserDTO getUserDtoById(Long id){
        User user = getUserById(id);
        return userMapper.map(user);
    }

    public User getUserById(Long id){
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        return user;
    }

    public void deleteUser(Long id){
        User user = getUserById(id);
        userRepository.delete(user);
    }

    public UserDTO updateUser(Long id, UserDTO userDTO){
        User user = getUserById(id);
        if(userDTO.getUsername()!=null) {
            user.setUsername(userDTO.getUsername());
        }
        if(userDTO.getPassword()!=null) {
            user.setPassword(userDTO.getPassword());
        }
        if(userDTO.getRoles().isEmpty()) {
            user.setRoles(user.getRoles());
        }

        return userMapper.map(userRepository.save(user));
    }

    public UserDTO getUserDtoByUsername(String username) {
        return userMapper.map(userRepository
                .findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found")));
    }

    public List<UserDTO> searchUserDtoByUsername(String username) {
        return userRepository
                .findByUsernameIgnoreCaseContaining(username)
                .stream()
                .map(user -> userMapper.map(user))
                .collect(Collectors.toList());
    }

}
