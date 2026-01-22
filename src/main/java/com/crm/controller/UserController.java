package com.crm.controller;

import com.crm.DTOs.UserRequestDTOs;
import com.crm.model.User;
import com.crm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/admin/")
    public String userSecurity() {
        return "User Logged IN";
    }

        @PostMapping("/add")
        public ResponseEntity<String> addUser(@RequestBody UserRequestDTOs userRequestDTO) {
            User u = new User();

            // FIX: Use setUsername() [lowercase 'n'] to match the model field and setter
            u.setUserName(userRequestDTO.getUserName());

            // FIX: Pass the raw password; your UserService.addUser() already encodes it
            u.setPassword(userRequestDTO.getPassword());
            u.setRole("USER"); // Default role for new users

            userService.addUser(u);

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("User added successfully");
        }
}

