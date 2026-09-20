package com.example.demo.model;

import java.sql.Date;
import java.util.HashSet;

import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table
public final class Client extends User{
    

    @ElementCollection
    @CollectionTable(name = "client_history", joinColumns = @jakarta.persistence.JoinColumn(name = "client_id"))
    private HashSet<Long> history;
    
    @ElementCollection
    @CollectionTable(name = "client_cart", joinColumns = @jakarta.persistence.JoinColumn(name = "client_id"))
    private HashSet<Long> cart;

    public Client(String name, String email, String password, Role role, Date createdAt, String address, 
                        Date updatedAt, HashSet<Long> history, HashSet<Long> cart) {
                            
        super(name, email, password, role, createdAt, address, updatedAt);
        this.history = history;
        this.cart = cart;
    }
}