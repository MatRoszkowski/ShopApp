package com.shop.shopapp.controllers;


import com.shop.shopapp.security.CurrentUser;
import com.shop.shopapp.security.UserPrincipal;
import com.shop.shopapp.services.UserService;
import com.shop.shopapp.services.dataTransferObjects.UserDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;


    private Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{username}")
    public UserDTO getUserByUsername(@PathVariable String username){
        logger.info("/user/{username} called");

        return userService.getUserDtoByUsername(username);
    }

    @PatchMapping("")
    public UserDTO updateUser(@CurrentUser UserPrincipal userPrincipal, @RequestBody UserDTO userDTO){

        return userService.updateUser(userPrincipal.getId(), userDTO);
    }

    @DeleteMapping("")
    public void deleteUser(@CurrentUser UserPrincipal userPrincipal){
        logger.info("/user delete called");
        userService.deleteUser(userPrincipal.getId());
    }

    @GetMapping("/search")
    public List<UserDTO> searchUser(@RequestParam String q) {

        logger.info("/user/search called");return userService.searchUserDtoByUsername(q);
    }

    @GetMapping("/logged/info")
    public UserDTO getLoggedUserInfo(@CurrentUser UserPrincipal user) {
        return userService.getUserDtoById(user.getId());
    }
}
