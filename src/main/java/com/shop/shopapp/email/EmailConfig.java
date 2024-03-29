package com.shop.shopapp.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class EmailConfig {

    @Value("${spring.mail.host}")
    private String host;
    @Value("${spring.mail.port}")
    private int port;
    @Value("${spring.mail.username}")
    private String username;
    @Value("${spring.mail.password}")
    private String password;

    public EmailConfig() {
    }

    public String getHost() {
        return host;
    }


    public int getPort() {
        return port;
    }


    public String getUsername() {
        return username;
    }


    public String getPassword() {
        return password;
    }
}
