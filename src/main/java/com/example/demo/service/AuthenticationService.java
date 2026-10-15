package com.example.demo.service;

import java.sql.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;

import com.example.demo.dto.LoginRequest;
import com.example.demo.dto.SigninRequest;
import com.example.demo.exception.exceptions.InvalidCredentialsException;
import com.example.demo.model.Client;
import com.example.demo.model.Role;
import com.example.demo.model.Supplier;
import com.example.demo.repository.ClientRepository;
import com.example.demo.repository.SupplierRepository;
import com.example.demo.security.JwtUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final JwtUtil jwtUtil;
    private final ClientRepository clientRepository;
    private final SupplierRepository supplierRepository;
    private static final int MAX_AGE = 3600;

    public ResponseCookie authenticate(LoginRequest loginRequest) {
        this.validateLoginRequestFields(loginRequest);

        Role role = validateCredentials(loginRequest);

        if(role == null) {
            log.error("User not found: {}", loginRequest.password());
            throw new InvalidCredentialsException("Sign in if you don't have an account yet!");
        }

        String token = this.generateTokenFromRequest(loginRequest.name(), role);

        return this.generateCookieFromToken(token);
    }

    public ResponseCookie register(SigninRequest signinRequest) {
        this.validateSigninRequestFields(signinRequest);

        String token = this.generateTokenFromRequest(signinRequest.name(), signinRequest.role());

        if(signinRequest.role().equals(Role.ROLE_CLIENT)) {
            try {this.clientRepository.save(new Client(
                signinRequest.name(), 
                signinRequest.email(), 
                signinRequest.password(), 
                signinRequest.role(), 
                new Date(System.currentTimeMillis()),  
                signinRequest.address(),
                new Date(System.currentTimeMillis()), 
                new HashSet<>(), 
                new HashSet<>()));
            } catch(Exception e) {
                throw new InvalidCredentialsException("This name is already in use. Log in or use another one!");
            }
        } else if(signinRequest.role().equals(Role.ROLE_SUPPLIER)) {
            try {this.supplierRepository.save(new Supplier(
                signinRequest.name(), 
                signinRequest.email(), 
                signinRequest.password(), 
                signinRequest.role(), 
                new Date(System.currentTimeMillis()),  
                signinRequest.address(),
                new Date(System.currentTimeMillis()), 
                new HashSet<>()));
            } catch (Exception e) {
                throw new InvalidCredentialsException("This name is already in use. Log in or use another one!");
            }
        } else if(signinRequest.role().equals(Role.ROLE_ADMIN)) {
            return null;
        } else {
            throw new IllegalArgumentException("Failed to register user.");
        }
        return this.generateCookieFromToken(token);
    }

    private String generateTokenFromRequest(String name, Role role) {
        Map<String, String> claims = new HashMap<>();
        claims.put("role", role.toString());

        return jwtUtil.generateToken(name, "http://localhost:5173", claims, MAX_AGE);
    }

    // private String generateTokenFromSignin(SigninRequest signinRequest) {
    //     Map<String, String> claims = new HashMap<>();
    //     claims.put("role", signinRequest.role().toString());

    //     return jwtUtil.generateToken(signinRequest.name(), "http://localhost:5173", claims, this.MAX_AGE);
    // }

    private ResponseCookie generateCookieFromToken(String token) {
        return ResponseCookie.from("token", token)
                .httpOnly(true)
                .secure(false) //Change in prod to true
                .path("/")
                .maxAge(MAX_AGE)
                .sameSite("Lax")
                .build();
    }

    private Role validateCredentials(LoginRequest loginRequest) {
        Optional<Client> client = this.clientRepository.findByName(loginRequest.name());
        Optional<Supplier> supplier = this.supplierRepository.findByName(loginRequest.name());
        
        if(client.isPresent()) {
            if(!client.get().getPassword().equals(loginRequest.password())) {
                log.error("Invalid password: {}", loginRequest.password());
                throw new InvalidCredentialsException("Invalid password for user " + loginRequest.name());
            }
            return Role.ROLE_CLIENT;
        } else if(supplier.isPresent()) {
            if(!supplier.get().getPassword().equals(loginRequest.password())) {
                log.error("Invalid password: {}", loginRequest.password());
                throw new InvalidCredentialsException("Invalid password for user " + loginRequest.name());
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
