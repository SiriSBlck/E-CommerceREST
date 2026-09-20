package com.example.demo.controller;

import java.net.http.HttpRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.dto.LoginRequest;
import com.example.demo.dto.LoginResponse;
import com.example.demo.model.Client;
import com.example.demo.model.Product;
import com.example.demo.security.JwtUtil;
import com.example.demo.service.ClientService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;




@Controller
@AllArgsConstructor
@RequestMapping("/client")
public final class ClientController {

    private ClientService clientService;
    private AuthenticationManager authenticationManager;
    private JwtUtil jwtUtil;

    @GetMapping(value="/{clientId}")
    public ResponseEntity<Client> requestMethodName(@PathVariable Long clientId) {
        return ResponseEntity.ok(this.clientService.getClient(clientId));
    }

    @PutMapping(value="/{clientId}")
    public ResponseEntity<Client> putClient(@RequestParam Client client) {        
        return ResponseEntity.ok(this.clientService.editClient(client));
    }
    
    @GetMapping(value="/{clientId}/cart")
    public Page<Product> getCart(@PathVariable Long clientId, @PageableDefault(size=20, sort="name") Pageable pageable) {
        return this.clientService.getCartProducts(clientId, pageable);
    } 

    @PostMapping(value="/{clientId}/cart/{productId}")
    public ResponseEntity<HttpRequest> addProductToCart(@PathVariable Long clientId, @PathVariable Long productId) {
        return (this.clientService.saveProductToCart(clientId, productId) ? 
            new ResponseEntity<>(HttpStatusCode.valueOf(200)) : new ResponseEntity<>(HttpStatusCode.valueOf(412)));
    }
    
    @DeleteMapping(value="/{clientId}/cart/{productId}")
    public ResponseEntity<HttpRequest> deleteProductFromCart(@PathVariable Long clientId, @PathVariable Long productId) {
        return (this.clientService.removeProductFromCart(clientId, productId) ? 
            new ResponseEntity<>(HttpStatusCode.valueOf(200)) : new ResponseEntity<>(HttpStatusCode.valueOf(412)));
    }

    @DeleteMapping(value="/{clientId}/cart")
    public ResponseEntity<HttpRequest> emptyCart(@PathVariable Long clientId) {
        return (this.clientService.emptyCart(clientId) ? 
            new ResponseEntity<>(HttpStatusCode.valueOf(200)) : new ResponseEntity<>(HttpStatusCode.valueOf(500)));
    }
}
