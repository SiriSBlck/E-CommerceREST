package com.example.demo.service;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.demo.model.Review;
import com.example.demo.repository.ReviewRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class ReviewService {

    private ReviewRepository reviewRepository;

    public boolean addReview(Review review) {
        return (this.reviewRepository.save(review) != null);
    }

    public void deleteReview(Long reviewId) {
        this.reviewRepository.deleteById(reviewId);
    }
}
