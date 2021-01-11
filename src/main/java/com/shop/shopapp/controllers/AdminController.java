package com.shop.shopapp.controllers;


import com.shop.shopapp.services.dataTransferObjects.UserDTO;
import com.shop.shopapp.services.dataTransferObjects.requests.AddRoleRequest;
import com.shop.shopapp.services.implementations.AdminService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@PreAuthorize("hasRole('ROLE_ADMIN')")
@RequestMapping("/admin")
public class AdminController {

    private Logger logger = LoggerFactory.getLogger(AdminController.class);

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PutMapping("/addRole")
    public void addRole(@RequestBody AddRoleRequest request){
        adminService.addRole(request.getUserId(),request.getRoleName());
    }

    @GetMapping("/getAllUsers")
    public Iterable<UserDTO> addRole(){
        Iterable<UserDTO> result = adminService.getAllUser();

        return result;
    }
}
