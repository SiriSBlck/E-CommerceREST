package com.example.demo.controller;

import java.net.http.HttpRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.model.Product;
import com.example.demo.service.ClientService;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("/client")
public final class ClientController {

    private ClientService clientService;

    @Autowired
    public void setClientService(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping(value="{client-id}")
    public String requestMethodName(@RequestParam String param) {
        return new String();
    }
    
    @GetMapping(value="/client/cart")
    public Page<Product> requestClientCart(@RequestParam Long clientId, @PageableDefault(size=20, sort="name") Pageable pageable) {
        return this.clientService.getCartProducts(clientId, pageable);
    } 

    @PutMapping(value="/client/cart")
    public ResponseEntity<HttpRequest> addProductToCart(@RequestParam Long clientId, @RequestParam Long productId) {
        return (this.clientService.saveProductToCart(clientId, productId) ? 
            new ResponseEntity<>(HttpStatusCode.valueOf(200)) : new ResponseEntity<>(HttpStatusCode.valueOf(412)));
    }
    
    @PutMapping(value="/client/cart")
    public ResponseEntity<HttpRequest> removeProductFromCart(@RequestParam Long clientId, @RequestParam Long productId) {
        return (this.clientService.removeProductFromCart(clientId, productId) ? 
            new ResponseEntity<>(HttpStatusCode.valueOf(200)) : new ResponseEntity<>(HttpStatusCode.valueOf(412)));
    }

    @PutMapping(value="/client/cart")
    public ResponseEntity<HttpRequest> emptyCart(@RequestParam Long clientId) {
        return (this.clientService.emptyCart(clientId) ? 
            new ResponseEntity<>(HttpStatusCode.valueOf(200)) : new ResponseEntity<>(HttpStatusCode.valueOf(500)));
    }
}
