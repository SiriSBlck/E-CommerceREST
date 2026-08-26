package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Supplier;

public interface SupplierRepository extends JpaRepository<Supplier, Long>{
    Optional<Supplier> findById(Long supplierId);
}
