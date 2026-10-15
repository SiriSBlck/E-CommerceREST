package com.example.demo.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.dto.LoginRequest;
import com.example.demo.dto.SigninRequest;
import com.example.demo.service.AuthenticationService;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@Controller
@RequiredArgsConstructor
@RequestMapping
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    
    @PostMapping("/login")
    public ResponseEntity<Void> authenticateUser(@RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, this.authenticationService.authenticate(loginRequest).toString()).build();
    }

    @PostMapping("/signin")
    public ResponseEntity<Void> postMethodName(@RequestBody SigninRequest signinRequest) {        
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, this.authenticationService.register(signinRequest).toString()).build();
    }
}
