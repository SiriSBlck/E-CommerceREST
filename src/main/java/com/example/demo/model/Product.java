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
public final class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private long id;
    
    private String name;
    
    private long supplierId;
    
    private double price;
    
    private int stock;
    
    private int score;

    @ElementCollection
    @CollectionTable(name = "product_reviews", joinColumns = @jakarta.persistence.JoinColumn(name = "product_id"))
    private List<Review> reviews;
}