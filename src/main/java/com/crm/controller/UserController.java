package com.crm.controller;

import com.crm.DTOs.UserRequestDTOs;
import com.crm.Enum.Role;
import com.crm.model.User;
import com.crm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;


    @PreAuthorize("hasAnyRole('EMPLOYEE','ADMIN')")
    @GetMapping("/admin")
    public String userSecurity() {
        return "User Logged IN";
    }

    @PreAuthorize("hasAnyRole('EMPLOYEE','ADMIN')")
    @GetMapping("/employee/about")
    public String about(){
        return "About public page";
    }
     @PostMapping("/add")
     public ResponseEntity<String> addUser(@RequestBody UserRequestDTOs userRequestDTO) {
            User u = new User();

            // FIX: Use setUsername() [lowercase 'n'] to match the model field and setter
            u.setUsername(userRequestDTO.getUsername());
            u.setEmail(userRequestDTO.getEmail()); // this is for email

            // FIX: Pass the raw password; your UserService.addUser() already encodes it
            u.setPassword(userRequestDTO.getPassword());
            u.setRole(Role.EMPLOYEE); // Default role for new users

            userService.addUser(u);

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("User added successfully");
    }
    @PostMapping("admin/register")
    public User createUser(@RequestBody User user){
        return userService.createUser(user);
    }
}

