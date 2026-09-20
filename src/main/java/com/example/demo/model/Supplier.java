package com.example.demo.model;

import java.sql.Date;
import java.util.HashSet;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@NoArgsConstructor
@Setter
@Entity
@Table
public final class Supplier extends User{

    @ElementCollection
    @CollectionTable(name = "supplier_catalog", joinColumns = @jakarta.persistence.JoinColumn(name = "supplier_id"))
    private HashSet<Long> catalog;

    public Supplier(String name, String email, String password, Role role, Date createdAt, String address, 
                        Date updatedAt, HashSet<Long> catalog) {
        super(name, email, password, role, createdAt, address, updatedAt);
        this.catalog = catalog;
    }
}