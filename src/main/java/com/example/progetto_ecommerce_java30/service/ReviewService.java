package com.example.progetto_ecommerce_java30.service;

import com.example.progetto_ecommerce_java30.entity.ReviewEntity;
import com.example.progetto_ecommerce_java30.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;

    @Autowired
    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    public ReviewEntity saveReview(ReviewEntity review) {
        return reviewRepository.save(review);
    }

    public List<ReviewEntity> getReviewsByProduct(Long productId) {
        return reviewRepository.findByProductId(productId);
    }

    public List<ReviewEntity> getReviewsByUser(Long userId) {
        return reviewRepository.findByUserId(userId);
    }
}