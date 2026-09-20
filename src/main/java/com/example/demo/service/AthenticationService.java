package com.example.demo.service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;

import com.example.demo.dto.LoginRequest;
import com.example.demo.dto.LoginResponse;
import com.example.demo.dto.SigninRequest;
import com.example.demo.dto.SigninResponse;
import com.example.demo.model.Client;
import com.example.demo.model.Role;
import com.example.demo.model.Supplier;
import com.example.demo.model.User;
import com.example.demo.repository.ClientRepository;
import com.example.demo.repository.SupplierRepository;
import com.example.demo.security.JwtUtil;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@NoArgsConstructor
@AllArgsConstructor
public final class AthenticationService {
    private JwtUtil jwtUtil;
    private ClientRepository clientRepository;
    private SupplierRepository supplierRepository;

    public LoginResponse authenticate(LoginRequest loginRequest) {
        this.validateLoginRequestFields(loginRequest);

        Role role = validateCredentials(loginRequest);

        if(role == null) {
            log.error("User not found: {}", loginRequest.password());
            throw new RuntimeException("Invalid user");
        }

        Map<String, String> claims = new HashMap<>();
        claims.put("role", role.toString());

        String token = jwtUtil.generateToken(loginRequest.name(), "http://localhost:5173", claims, 3600);

        return LoginResponse.builder().name(loginRequest.name()).role(role).token(token).build();
    }

    public User register(SigninRequest registerRequest) {
        this.validateSigninRequestFields(registerRequest);

        if(registerRequest.role().equals(Role.ROLE_CLIENT)) {
            return this.clientRepository.save(new Client(
                registerRequest.name(), 
                registerRequest.email(), 
                registerRequest.password(), 
                registerRequest.role(), 
                new Date(System.currentTimeMillis()),  
                registerRequest.address(),
                new Date(System.currentTimeMillis()), 
                new HashSet<>(), 
                new HashSet<>()));
        } else if(registerRequest.role().equals(Role.ROLE_SUPPLIER)) {
            return this.supplierRepository.save(new Supplier(
                registerRequest.name(), 
                registerRequest.email(), 
                registerRequest.password(), 
                registerRequest.role(), 
                new Date(System.currentTimeMillis()),  
                registerRequest.address(),
                new Date(System.currentTimeMillis()), 
                new HashSet<>()));
        } else if(registerRequest.role().equals(Role.ROLE_ADMIN)) {
            return null;
        } else {
            throw new IllegalArgumentException("Failed to register user role.");
        }
    }

    private Role validateCredentials(LoginRequest loginRequest) {
        Optional<Client> client = this.clientRepository.findByName(loginRequest.name());
        Optional<Supplier> supplier = this.supplierRepository.findByName(loginRequest.name());
        
        if(client.isPresent()) {
            if(!client.get().getPassword().equals(loginRequest.password())) {
                log.error("Invalid password: {}", loginRequest.password());
                throw new RuntimeException("Invalid password");
            }
            return Role.ROLE_CLIENT;
        } else if(supplier.isPresent()) {
            if(!supplier.get().getPassword().equals(loginRequest.password())) {
                log.error("Invalid password: {}", loginRequest.password());
                throw new RuntimeException("Invalid password");
            }
            return Role.ROLE_SUPPLIER;
        } else {
            return null;
        }
    }

    private void validateLoginRequestFields(LoginRequest loginRequest) {
        Objects.requireNonNull(loginRequest, "Username and password cannot be null");
        Objects.requireNonNull(loginRequest.name(), "Username cannot be null");
        Objects.requireNonNull(loginRequest.password(), "Password cannot be null");
    }

    private void validateSigninRequestFields(SigninRequest signinRequest) {
        Objects.requireNonNull(signinRequest, "Required fields cannot be null");
        Objects.requireNonNull(signinRequest.name(), "Username cannot be null");
        Objects.requireNonNull(signinRequest.password(), "Password cannot be null");
        Objects.requireNonNull(signinRequest.email(), "Email cannot be null");
        Objects.requireNonNull(signinRequest.role(), "User role cannot be null");
    }
}
