package com.shop.shopapp.services.implementations;

import com.shop.shopapp.infrastructure.entities.RoleName;
import com.shop.shopapp.infrastructure.entities.User;
import com.shop.shopapp.infrastructure.repositories.RoleRepository;
import com.shop.shopapp.infrastructure.repositories.UserRepository;
import com.shop.shopapp.services.dataTransferObjects.UserDTO;
import com.shop.shopapp.services.dataTransferObjects.mappers.UserMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Service
public class AdminService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;

    public AdminService(UserRepository userRepository,
                        RoleRepository roleRepository,
                        UserMapper userMapper) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.userMapper = userMapper;
    }

    public void addRole(long userId, RoleName role){
        Optional<User> userQ = userRepository.findById(userId);

        if(!userQ.isPresent()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"User not found");
        }

        User user = userQ.get();

        user.getRoles().add(roleRepository.findByRoleName(role).orElseThrow(() -> new RuntimeException("Role doesn't exists")));

        userRepository.save(user);
    }

    public Iterable<UserDTO> getAllUser(){
        Iterable<User> users = userRepository.findAll();

        List<UserDTO> result = new ArrayList<>();
        Iterator<User> iterator = users.iterator();
        while(iterator.hasNext()){
            User current = iterator.next();
            result.add(userMapper.map(current));
        }
        return result;
    }
}
