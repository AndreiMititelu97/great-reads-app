package org.greatreads.service;

import org.greatreads.dto.review.ReviewDTO;
import org.greatreads.dto.review.ReviewResponseDTO;
import org.greatreads.model.Review;

import java.util.List;

public interface ReviewService {
    Review addReview(int bookId, ReviewDTO reviewDTO);
    ReviewResponseDTO getReview(int reviewId);
    Review updateReview(int reviewId, ReviewDTO reviewDTO);
    void deleteReview(int reviewId);
    List<ReviewResponseDTO> getAllReviewsByBookId(int bookId);
    List<ReviewResponseDTO> getAllReviewsByUserId(int userId);
    double getAverageRatingForBook(int bookId);

}
