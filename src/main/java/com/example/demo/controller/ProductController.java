package com.example.demo.controller;

import java.net.http.HttpRequest;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.model.Product;
import com.example.demo.model.Review;
import com.example.demo.service.ProductService;
import com.example.demo.service.ReviewService;

import lombok.AllArgsConstructor;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:5173")
@AllArgsConstructor
@RestController
@RequestMapping
public class ProductController {

    private final ReviewService reviewService;
    private final ProductService productService;

    @GetMapping("/product")
    public ResponseEntity<Page<Product>> getProducts(@PageableDefault(size=20, sort="name") Pageable pageable) {
        return ResponseEntity.ok(this.productService.getAllProducts(pageable));
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<Product> getProduct(@PathVariable Long productId) {
        return ResponseEntity.ok(this.productService.getProduct(productId));
    }

    @GetMapping("/product/{productId}/review")
    public ResponseEntity<Page<Review>> getProductReviews(@PathVariable Long productId, @PageableDefault(size = 20, sort = "createdAt") Pageable pageable) {
        return ResponseEntity.ok(this.productService.getReviews(productId, pageable));
    }

    @PutMapping("/product/{productId}/review")
    public ResponseEntity<HttpRequest> addProductReview(@RequestParam Review review) { 
        return (this.reviewService.addReview(review) ? new ResponseEntity<>(HttpStatusCode.valueOf(200)) : 
                                                new ResponseEntity<>(HttpStatusCode.valueOf(412)));
    }
    
    @DeleteMapping("/product/{productId}/review")
    public ResponseEntity<HttpRequest> deleteProductReview(@RequestParam Long reviewId) {
        this.reviewService.deleteReview(reviewId);
        return new ResponseEntity<>(HttpStatusCode.valueOf(200));
    }
}
