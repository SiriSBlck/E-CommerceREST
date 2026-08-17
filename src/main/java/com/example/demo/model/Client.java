package com.example.demo.model;

import java.util.List;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Entity
public final class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "client_id")
    private Long id;

    private String name;

    private String email;

    @ElementCollection
    @CollectionTable(name = "client_history", joinColumns = @jakarta.persistence.JoinColumn(name = "client_id"))
    private List<Product> history;
    
    @ElementCollection
    @CollectionTable(name = "client_cart", joinColumns = @jakarta.persistence.JoinColumn(name = "client_id"))
    private List<Long> cart;
}