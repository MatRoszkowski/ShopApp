package com.shop.shopapp.controllers;


import com.shop.shopapp.services.AuthService;
import com.shop.shopapp.services.dataTransferObjects.UserDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public String login(@RequestBody UserDTO userDTO) {
        logger.info("/auth/login called");
        String result = authService.login(userDTO);

        logger.info("/auth/login call completed successfully");

        return result;
    }

    @PostMapping("/register")
    public UserDTO register(@RequestBody UserDTO userDTO) {
        logger.info("/auth/register called");

        UserDTO result = authService.createUser(userDTO);
        logger.info("/auth/register call completed successfully");
        return result;
    }
}
