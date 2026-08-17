package com.example.demo.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.demo.model.Client;
import com.example.demo.model.Product;
import com.example.demo.repository.ClientRepository;
import com.example.demo.repository.ProductRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public final class ClientService {
    private final ClientRepository clientRepository;
    private final ProductRepository productRepository;

    public void registerClient(Client client) {
        this.clientRepository.save(client);
    }

    public Page<Product> getCartProducts(Long clientId, Pageable pageable) {
        Client client = this.clientRepository.findById(clientId).orElseThrow(() -> new RuntimeException("Client not found"));
        return this.productRepository.findAllById(client.getCart(), pageable);
    }
}
