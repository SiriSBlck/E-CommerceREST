package com.example.demo.dto;

import com.example.demo.model.Role;

import lombok.Builder;

@Builder 
public record LoginResponse(String name, Role role, String token) {

}
