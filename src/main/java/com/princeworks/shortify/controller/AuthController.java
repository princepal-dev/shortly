package com.princeworks.shortify.controller;

import com.princeworks.shortify.dto.request.RegisterDTO;
import com.princeworks.shortify.dto.response.RegisterResponse;
import com.princeworks.shortify.service.auth.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("/api/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(@RequestBody RegisterDTO registerDTO) {
        RegisterResponse registerResponse = authService.register(registerDTO);
        return new ResponseEntity<>(registerResponse, HttpStatus.CREATED);
    }

    // TODO: Implement user login
    // TODO: Sign out user
}
