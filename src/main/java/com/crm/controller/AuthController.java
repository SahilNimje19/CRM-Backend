package com.crm.controller;

import com.crm.DTOs.LoginReqDto;
import com.crm.utils.JwtUtil;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthenticationManager  authenticationManager;
    private final JwtUtil jwtUtil;

    public AuthController(AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }


    @PostMapping("/login")
    public String login(@RequestBody LoginReqDto loginReqDto) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginReqDto.getEmail(), loginReqDto.getPassword())
        );
        return jwtUtil.generateToken(loginReqDto.getEmail());
    }

}
