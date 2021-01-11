package com.shop.shopapp.servicesTests;

import com.shop.shopapp.infrastructure.entities.RoleName;
import com.shop.shopapp.infrastructure.entities.User;
import com.shop.shopapp.infrastructure.repositories.UserRepository;
import com.shop.shopapp.services.AuthService;
import com.shop.shopapp.services.dataTransferObjects.UserDTO;
import javafx.application.Application;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.HashSet;
import java.util.Set;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class AuthServiceTest {

    @Autowired
    private AuthService authService;

    @Autowired
    private UserRepository userRepository;

    private UserDTO userDTO;

    @BeforeEach
    void setup() {
        User user = userRepository.findByUsername("nazwaUzytkownika").orElse(new User());
        userRepository.delete(user);
        userDTO = new UserDTO();
        userDTO.setUsername("nazwaUzytkownika");
        userDTO.setEmail("email@gmail.com");
        userDTO.setPassword("haslo");
        userDTO.setFirstName("imie");
        userDTO.setLastName("nazwisko");
        userDTO.setAddress("Białystok");
        userDTO.setPhoneNumber("987654321");
        Set<RoleName> set = new HashSet<RoleName>();
        RoleName roleName = RoleName.ROLE_CUSTOMER;
        set.add(roleName);
        userDTO.setRoles(set);
    }

    @AfterEach
    void reset(){
        User user = userRepository.findByUsername("nazwaUzytkownika").orElse(new User());
        userRepository.delete(user);
    }

    @Test
    @DisplayName("działanie metody createUser()-rejestracja użytkownika")
    public void createUser(){
        //given
        authService.createUser(userDTO);
        //when
        User user = userRepository.findByUsername("nazwaUzytkownika").orElse(new User());
        //then
        assert user.getUsername().equals(userDTO.getUsername());
    }
}
