package com.example.demo.controller;

import com.example.demo.repository.ReviewRepository;

import java.net.http.HttpRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.model.Product;
import com.example.demo.model.Review;
import com.example.demo.service.ProductService;
import com.example.demo.service.ReviewService;

import lombok.AllArgsConstructor;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@AllArgsConstructor
@Controller
@RequestMapping("/product")
public final class ProductController {

    private final ReviewService reviewService;
    private final ProductService productService;

    @GetMapping("/product")
    public ResponseEntity<Page<Product>> getProducts(@PageableDefault(size=20, sort="name") Pageable pageable) {
        return ResponseEntity.ok(this.productService.getAllProducts(pageable));
    }

    @GetMapping("/product/{product-id}")
    public ResponseEntity<Product> getProduct(@RequestParam Long productId) {
        return ResponseEntity.ok(this.productService.getProduct(productId));
    }

    @GetMapping("/product/{product-id}/review")
    public ResponseEntity<Page<Review>> getProductReviews(@RequestParam Long productId, @PageableDefault(size = 20, sort = "createdAt") Pageable pageable) {
        return ResponseEntity.ok(this.productService.getReviews(productId, pageable));
    }

    @GetMapping("/product/{product-id}/review")
    public ResponseEntity<HttpRequest> addProductReview(@RequestParam Review review, @PageableDefault(size = 20, sort = "createdAt") Pageable pageable) { 
        return (this.reviewService.addReview(review, pageable) ? new ResponseEntity<>(HttpStatusCode.valueOf(200)) : 
                                                new ResponseEntity<>(HttpStatusCode.valueOf(412)));
    }
    
    @DeleteMapping("/product/{product-id}/review")
    public ResponseEntity<HttpRequest> deleteProductReview(@RequestParam Long productId) {
        this.reviewService.deleteReview(productId);
        return new ResponseEntity<>(HttpStatusCode.valueOf(200));
    }
}
