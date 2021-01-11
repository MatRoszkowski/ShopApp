package com.shop.shopapp.services.implementations;

import com.shop.shopapp.email.EmailConfig;
import com.shop.shopapp.infrastructure.entities.Email;
import com.shop.shopapp.infrastructure.entities.User;
import com.shop.shopapp.infrastructure.repositories.EmailRepository;
import com.shop.shopapp.infrastructure.repositories.UserRepository;
import com.shop.shopapp.services.EmailService;
import com.shop.shopapp.services.dataTransferObjects.EmailDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import java.util.Properties;


@Service
public class EmailServiceImpl implements EmailService {

    private final UserRepository userRepository;
    private final EmailRepository emailRepository;
    @Autowired
    private EmailConfig emailConfig;


    public EmailServiceImpl(UserRepository userRepository, EmailRepository emailRepository) {
        this.userRepository = userRepository;
        this.emailRepository = emailRepository;
    }


    @Override
    public Email sendEmail(String username, EmailDTO emailDTO) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));
        // Create a mail sender
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        Properties properties = mailSender.getJavaMailProperties();
        properties.put("mail.smtp.starttls.enable", "true");
        mailSender.setHost(emailConfig.getHost());
        mailSender.setPort(emailConfig.getPort());
        mailSender.setUsername(emailConfig.getUsername());
        mailSender.setPassword(emailConfig.getPassword());

        // Create an email instance
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom("Najlepszysklep@gmail.com");
        mailMessage.setTo(user.getEmail());
        mailMessage.setSubject(emailDTO.getSubject());
        mailMessage.setText(emailDTO.getMessage());

        Email email = new Email();

        email.setUser(user);
        email.setSubject(emailDTO.getSubject());
        email.setMessage(emailDTO.getMessage());

        // Send mail
        try{
            mailSender.send(mailMessage);
            emailRepository.save(email);
            return email;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Email sendEmail(long userId, EmailDTO emailDTO) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        return sendEmail(user.getUsername(), emailDTO);
    }

    @Override
    public Email sendEmail(User user, EmailDTO emailDTO) {
        return sendEmail(user.getUsername(), emailDTO);
    }
}
