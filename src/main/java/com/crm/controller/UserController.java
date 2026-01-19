package com.crm.controller;
import com.crm.model.User;
import com.crm.service.CustomerUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class UserController {

    @Autowired
    private CustomerUserDetailsService customerUserDetailsService;

    @GetMapping
    public String userSecurity() {
        return "User Logged IN";
    }

    @PostMapping("/add")
    public ResponseEntity<String> addUser(@RequestBody User user) {
        customerUserDetailsService.addUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body("User added successfully");
    }
}
