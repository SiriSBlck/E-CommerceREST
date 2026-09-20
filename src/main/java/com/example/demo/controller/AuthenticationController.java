package com.example.demo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.dto.LoginRequest;
import com.example.demo.dto.LoginResponse;
import com.example.demo.dto.SigninRequest;
import com.example.demo.model.User;
import com.example.demo.service.AthenticationService;

import lombok.AllArgsConstructor;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@Controller
@AllArgsConstructor
@RequestMapping
public final class AuthenticationController {

    private AthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticateUser(@RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(this.authenticationService.authenticate(loginRequest));
    }

    @PostMapping("/signin")
    public ResponseEntity<User> postMethodName(@RequestBody SigninRequest signinRequest) {        
        return ResponseEntity.ok(this.authenticationService.register(signinRequest));
    }
}
