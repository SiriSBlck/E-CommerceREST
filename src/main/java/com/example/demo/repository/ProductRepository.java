package com.example.demo.repository;

import java.util.Optional;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Page<Product> findAllByIdIn(Set<Long> productIds, Pageable pageable);
    Page<Product> findAll(Pageable pageable);
    Optional<Product> findById(Long productId);
    Page<Product> findAllBySupplierId(Long supplierId, Pageable pageable);
}
