package org.greatreads.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.greatreads.dto.review.ReviewDTO;
import org.greatreads.dto.review.ReviewResponseDTO;
import org.greatreads.dto.review.UpdateReviewDTO;
import org.greatreads.service.ReviewService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reviews")
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    @PostMapping
    public ResponseEntity<ReviewResponseDTO> addReview(@RequestBody @Valid ReviewDTO reviewDTO) {
        ReviewResponseDTO savedReview = reviewService.addReview(reviewDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedReview);
    }

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

    @PatchMapping("/{reviewId}")
    public ResponseEntity<ReviewResponseDTO> updateReview(@PathVariable int reviewId,
                                                          @RequestBody @Valid UpdateReviewDTO updateReviewDTO) {
        return ResponseEntity.ok(reviewService.updateReview(reviewId, updateReviewDTO));
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<Void> deleteReview(@PathVariable int reviewId) {
        reviewService.deleteReview(reviewId);
        return ResponseEntity.noContent().build();
    }
}
