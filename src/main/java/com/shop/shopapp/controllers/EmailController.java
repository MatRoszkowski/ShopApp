package com.shop.shopapp.controllers;

import com.shop.shopapp.infrastructure.entities.Email;
import com.shop.shopapp.services.EmailService;
import com.shop.shopapp.services.dataTransferObjects.EmailDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/email")
public class EmailController {

    private final EmailService emailService;

    @Autowired
    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping
    public Email sendEmail(@RequestParam String username, @RequestBody EmailDTO emailDTO) {
        return emailService.sendEmail(username, emailDTO);
    }
}
