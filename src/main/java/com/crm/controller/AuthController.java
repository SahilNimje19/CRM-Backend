package com.crm.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.crm.DTOs.AuthResponse;
import com.crm.DTOs.LoginReqDto;
import com.crm.DTOs.RegisterRequest;
import com.crm.Enum.Role;
import com.crm.model.User;
import com.crm.service.UserService;
import com.crm.utils.JwtUtil;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserService userService;
    private final UserDetailsService userDetailsService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginReqDto loginReqDto) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginReqDto.getEmail(),
                        loginReqDto.getPassword()));
        
        UserDetails userDetails = userDetailsService.loadUserByUsername(loginReqDto.getEmail());
        String token = jwtUtil.generateToken(loginReqDto.getEmail());
        
        User user = (User) userDetails;
        AuthResponse.UserInfo userInfo = new AuthResponse.UserInfo(
            user.getId(),
            user.getUsername(),
            user.getRole() != null ? user.getRole().name() : "EMPLOYEE"
        );
        
        AuthResponse response = new AuthResponse(token, null, userInfo);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) {
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(request.getPassword());
        user.setRole(Role.EMPLOYEE);
        
        User savedUser = userService.addUser(user);
        String token = jwtUtil.generateToken(savedUser.getUsername());
        
        AuthResponse.UserInfo userInfo = new AuthResponse.UserInfo(
            savedUser.getId(),
            savedUser.getUsername(),
            savedUser.getRole().name()
        );
        
        AuthResponse response = new AuthResponse(token, null, userInfo);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/me")
    public ResponseEntity<AuthResponse.UserInfo> getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            User user = (User) authentication.getPrincipal();
            AuthResponse.UserInfo userInfo = new AuthResponse.UserInfo(
                user.getId(),
                user.getUsername(),
                user.getRole() != null ? user.getRole().name() : "EMPLOYEE"
            );
            return ResponseEntity.ok(userInfo);
        }
        
        return ResponseEntity.status(401).build();
    }

}
