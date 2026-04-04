package com.crm.controller;

import com.crm.DTOs.UserRequestDTOs;
import com.crm.Enum.Role;
import com.crm.model.User;
import com.crm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class UserController {

    @Autowired
    private UserService userService;


    @GetMapping("/admin/")
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
        // 👇 Notice the capital 'N' here
        u.setUsername(userRequestDTO.getUserName());

        u.setEmail(userRequestDTO.getEmail());
        u.setPassword(userRequestDTO.getPassword());
        u.setRole(Role.EMPLOYEE);

        userService.addUser(u);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body("User added successfully");
    }

    @PostMapping("admin/register")
    public User createUser(@RequestBody User user){
        return userService.createUser(user);
    }
}

