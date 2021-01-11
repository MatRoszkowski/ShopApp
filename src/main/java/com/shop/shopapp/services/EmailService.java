package com.shop.shopapp.services;

import com.shop.shopapp.infrastructure.entities.Email;
import com.shop.shopapp.infrastructure.entities.User;
import com.shop.shopapp.services.dataTransferObjects.EmailDTO;
import com.shop.shopapp.services.dataTransferObjects.UserDTO;

public interface EmailService {
    Email sendEmail(String username, EmailDTO emailDTO);
    Email sendEmail(long userId, EmailDTO emailDTO);
    Email sendEmail(User user, EmailDTO emailDTO);
}
