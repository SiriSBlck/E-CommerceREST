package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Client;

import java.util.Optional;



public interface ClientRepository extends JpaRepository<Client, Long> {
    Optional<Client> findById(Long clientId);
    Client save(Client client);
}
