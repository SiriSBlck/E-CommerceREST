package com.example.demo.dto;

import com.example.demo.model.Role;

import lombok.Builder;

@Builder 
public final record SigninRequest(String name, String password, String email, Role role, String address) {}
