package com.example.progetto_ecommerce_java30.controller;

import com.example.progetto_ecommerce_java30.entity.ReviewEntity;
import com.example.progetto_ecommerce_java30.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
public class ReviewController {

    private final ReviewService reviewService;

    @Autowired
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping("/reviews")
    public ResponseEntity<ReviewEntity> createReview(@RequestBody ReviewEntity review) {
        ReviewEntity savedReview = reviewService.saveReview(review);
        return new ResponseEntity<>(savedReview, HttpStatus.CREATED);
    }

    @GetMapping("/products/{productId}/reviews")
    public ResponseEntity<List<ReviewEntity>> getReviewsByProductId(@PathVariable Long productId) {
        List<ReviewEntity> reviews = reviewService.getReviewsByProduct(productId);
        return new ResponseEntity<>(reviews, HttpStatus.OK);
    }

    @GetMapping("/users/{userId}/reviews")
    public ResponseEntity<List<ReviewEntity>> getReviewsByUserId(@PathVariable Long userId) {
        List<ReviewEntity> reviews = reviewService.getReviewsByUser(userId);
        return new ResponseEntity<>(reviews, HttpStatus.OK);
    }
}