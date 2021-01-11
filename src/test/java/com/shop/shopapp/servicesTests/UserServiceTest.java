package com.shop.shopapp.servicesTests;

import com.shop.shopapp.infrastructure.entities.RoleName;
import com.shop.shopapp.infrastructure.entities.User;
import com.shop.shopapp.infrastructure.repositories.UserRepository;
import com.shop.shopapp.services.AuthService;
import com.shop.shopapp.services.UserService;
import com.shop.shopapp.services.dataTransferObjects.UserDTO;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.HashSet;
import java.util.Set;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class UserServiceTest {

    @Autowired
    private AuthService authService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    private UserDTO userDTO;

    @BeforeEach
    void setup() {
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
        authService.createUser(userDTO);
    }

    @AfterEach
    void reset(){
        User user = userRepository.findByUsername("nazwaUzytkownika").orElse(new User());
        userRepository.delete(user);
    }
    @Test
    @DisplayName("działanie metody deleteUser()-usuwanie użytkownika")
    void deleteUser(){
        //given
        User user = userRepository.findByUsername("nazwaUzytkownika").orElse(new User());
        //when
        userRepository.delete(user);
        User user1 = userRepository.findByUsername("nazwaUzytkownika").orElse(new User());
        //then
        assert user1.getId() == 0;
    }
    @Test
    @DisplayName("działanie metody updateUser()-aktualizowanie użytkownika")
    void updateUser(){
        //given
        userDTO = new UserDTO();
        userDTO.setUsername("nazwaUzytkownika2");
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
        User user = userRepository.findByUsername("nazwaUzytkownika").orElse(new User());
        long id = user.getId();
        //when
        userService.updateUser(id, userDTO);
        //then
        User user1 = userRepository.findById(id).orElse(new User());
        assert user1.getId() == id;
        assert user1.getUsername().equals("nazwaUzytkownika2");
    }
}
