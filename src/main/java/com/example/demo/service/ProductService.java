package com.example.demo.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.demo.model.Product;
import com.example.demo.model.Review;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.ReviewRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public final class ProductService {

    private final ProductRepository productRepository;
    private final ReviewRepository reviewRepository;

    public Product getProduct(Long productId) {
        return (this.productRepository.findById(productId).orElseThrow(() -> new RuntimeException("Product not found")));
    }

    public Page<Product> getAllProducts(Pageable pageable) {
        return this.productRepository.findAll(pageable);
    }

    public Page<Review> getReviews(Long productId, Pageable pageable) {
        if(!this.productRepository.findById(productId).isPresent()) {
            throw new IllegalArgumentException("Product not found");
        }
        return this.reviewRepository.findByProductId(productId, pageable);
    }
}
