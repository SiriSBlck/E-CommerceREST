package com.example.demo.model;

import java.sql.Date;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.example.demo.persistence.CustomId;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@NoArgsConstructor
@MappedSuperclass
@Getter
public abstract class User {
    @Id
    @CustomId()
    @Column(name = "user_id")
    private Long id;

    @NonNull
    @Column(nullable = false, unique = true)
    private String name;

    @NonNull
    @Column(nullable = false)
    private String email;
    
    @NonNull
    @Column(nullable = false)
    private String password;

    private Role role;

    @CreationTimestamp
    private Date createdAt;

    private String address;

    @UpdateTimestamp
    private Date updatedAt;
    
    public User(String name, String email, String password, Role role, Date createdAt, String address, Date updatedAt) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
        this.createdAt = createdAt;
        this.address = address;
        this.updatedAt = updatedAt;
    }
}
