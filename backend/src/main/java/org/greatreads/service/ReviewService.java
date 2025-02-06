package org.greatreads.service;

import org.greatreads.dto.review.ReviewDTO;
import org.greatreads.dto.review.ReviewResponseDTO;

import java.util.List;

public interface ReviewService {
    ReviewResponseDTO addReview(ReviewDTO reviewDTO);
    ReviewResponseDTO getReview(int reviewId);
    ReviewResponseDTO updateReview(int reviewId, ReviewDTO reviewDTO);
    void deleteReview(int reviewId);
    List<ReviewResponseDTO> getAllReviewsByBookId(int bookId);
    List<ReviewResponseDTO> getAllReviewsByUserId(int userId);
    double getAverageRatingForBook(int bookId);

}
