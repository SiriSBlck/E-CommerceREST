package com.example.demo.controller;

import java.net.http.HttpRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.model.Client;
import com.example.demo.model.Product;
import com.example.demo.service.ClientService;
import org.springframework.web.bind.annotation.RequestParam;



@Controller
@RequestMapping
public final class ClientController {

    private ClientService clientService;

    @Autowired
    public void setClientService(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping(value="/client/{clientId}")
    public ResponseEntity<Client> requestMethodName(@PathVariable Long clientId) {
        return ResponseEntity.ok(this.clientService.getClient(clientId));
    }

    @PutMapping(value="/client/{clientId}")
    public ResponseEntity<Client> putClient(@RequestParam Client client) {        
        return ResponseEntity.ok(this.clientService.editClient(client));
    }
    
    @GetMapping(value="/client/{clientId}/cart")
    public Page<Product> getCart(@PathVariable Long clientId, @PageableDefault(size=20, sort="name") Pageable pageable) {
        return this.clientService.getCartProducts(clientId, pageable);
    } 

    @PostMapping(value="/client/{clientId}/cart/{productId}")
    public ResponseEntity<HttpRequest> addProductToCart(@PathVariable Long clientId, @PathVariable Long productId) {
        return (this.clientService.saveProductToCart(clientId, productId) ? 
            new ResponseEntity<>(HttpStatusCode.valueOf(200)) : new ResponseEntity<>(HttpStatusCode.valueOf(412)));
    }
    
    @DeleteMapping(value="/client/{clientId}/cart/{productId}")
    public ResponseEntity<HttpRequest> deleteProductFromCart(@PathVariable Long clientId, @PathVariable Long productId) {
        return (this.clientService.removeProductFromCart(clientId, productId) ? 
            new ResponseEntity<>(HttpStatusCode.valueOf(200)) : new ResponseEntity<>(HttpStatusCode.valueOf(412)));
    }

    @DeleteMapping(value="/client/{clientId}/cart")
    public ResponseEntity<HttpRequest> emptyCart(@PathVariable Long clientId) {
        return (this.clientService.emptyCart(clientId) ? 
            new ResponseEntity<>(HttpStatusCode.valueOf(200)) : new ResponseEntity<>(HttpStatusCode.valueOf(500)));
    }
}
