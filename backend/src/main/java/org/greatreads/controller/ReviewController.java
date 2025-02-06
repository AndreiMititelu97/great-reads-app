package org.greatreads.controller;

import lombok.RequiredArgsConstructor;
import org.greatreads.dto.review.ReviewResponseDTO;
import org.greatreads.service.ReviewService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/reviews")
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    @GetMapping("/{reviewId}")
    public ResponseEntity<ReviewResponseDTO> getReview(@PathVariable int reviewId) {
        return ResponseEntity.ok(reviewService.getReview(reviewId));
    }

    @GetMapping("/books/{bookId}")
    public ResponseEntity<List<ReviewResponseDTO>> getAllReviewsByBookId(@PathVariable int bookId) {
        return ResponseEntity.ok(reviewService.getAllReviewsByBookId(bookId));
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<List<ReviewResponseDTO>> getAllReviewsByUserId(@PathVariable int userId) {
        return ResponseEntity.ok(reviewService.getAllReviewsByUserId(userId));
    }

    @GetMapping("/books/{bookId}/rating")
    public ResponseEntity<Double> getAverageRatingForBook(@PathVariable int bookId) {
        return ResponseEntity.ok(reviewService.getAverageRatingForBook(bookId));
    }
}
