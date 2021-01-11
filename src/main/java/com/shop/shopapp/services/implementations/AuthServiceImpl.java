package com.shop.shopapp.services.implementations;

import com.shop.shopapp.controllers.ProductController;
import com.shop.shopapp.infrastructure.entities.Role;
import com.shop.shopapp.infrastructure.entities.RoleName;
import com.shop.shopapp.infrastructure.entities.User;
import com.shop.shopapp.infrastructure.repositories.RoleRepository;
import com.shop.shopapp.infrastructure.repositories.UserRepository;
import com.shop.shopapp.security.JWTProvider;
import com.shop.shopapp.services.AuthService;
import com.shop.shopapp.services.dataTransferObjects.UserDTO;
import com.shop.shopapp.services.dataTransferObjects.mappers.UserMapper;
import io.jsonwebtoken.Jwts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Service
public class AuthServiceImpl implements AuthService, ApplicationRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final AuthenticationManager authenticationManager;
    private final JWTProvider jwtProvider;
    private final UserMapper userMapper;

    private Logger logger = LoggerFactory.getLogger(AuthServiceImpl.class);
    public AuthServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository,
                       AuthenticationManager authenticationManager, JWTProvider jwtProvider, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.authenticationManager = authenticationManager;
        this.jwtProvider = jwtProvider;
        this.userMapper = userMapper;
    }

    public UserDTO createUser(UserDTO userDTO) {
        User user = new User();
        if (checkUsername(userDTO.getUsername())) {
            user.setUsername(userDTO.getUsername());
        } else {
            throw new RuntimeException("That username is taken");
        }
        user.setEmail(userDTO.getEmail());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setAddress(userDTO.getAddress());
        user.setPhoneNumber(userDTO.getAddress());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));

        Set<Role> roles = new HashSet<>();
        roles.add(roleRepository.findByRoleName(RoleName.ROLE_CUSTOMER).orElseThrow(()->new ResponseStatusException(HttpStatus.BAD_REQUEST,"Role not found")));
        user.setRoles(roles);
        User result = userRepository.save(user);
        logger.info("Created new user #"+result.getId()+" "+result.getUsername());
        return userMapper.map(user);
    }

    private boolean checkUsername(String username){
        return userRepository.findAll(username).stream().noneMatch(u -> u.getUsername().equals(username));
    }

    public String login(UserDTO userDTO) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userDTO.getUsername(), userDTO.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtProvider.generateToken(authentication);

        return token;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if(roleRepository.count() == 0){
            Role cust = new Role();
            cust.setRoleName(RoleName.ROLE_CUSTOMER);
            roleRepository.save(cust);
            Role store = new Role();
            store.setRoleName(RoleName.ROLE_STOREKEEPER);
            roleRepository.save(store);
            Role admin = new Role();
            admin.setRoleName(RoleName.ROLE_ADMIN);
            roleRepository.save(admin);
        }
        if(!userRepository.findByUsername("admin").isPresent()){
            User user = new User();
            user.setUsername("admin");
            user.setPassword(passwordEncoder.encode("admin"));
            user.setEmail("");
            user.setFirstName("");
            user.setLastName("");
            user.setAddress("");
            user.setPhoneNumber("");
            Set<Role> roles = new HashSet<>();
            roles.add(roleRepository.findByRoleName(RoleName.ROLE_CUSTOMER).orElseThrow(()->new ResponseStatusException(HttpStatus.BAD_REQUEST,"Role not found")));
            roles.add(roleRepository.findByRoleName(RoleName.ROLE_STOREKEEPER).orElseThrow(()->new ResponseStatusException(HttpStatus.BAD_REQUEST,"Role not found")));
            roles.add(roleRepository.findByRoleName(RoleName.ROLE_ADMIN).orElseThrow(()->new ResponseStatusException(HttpStatus.BAD_REQUEST,"Role not found")));
            user.setRoles(roles);
            userRepository.save(user);
        }
    }
}
