package org.greatreads.service;

import org.greatreads.dto.ReviewDTO;
import org.greatreads.model.Review;

public interface ReviewService {
    Review addReview(int bookId, ReviewDTO reviewDTO);
    Review updateReview(int reviewId, ReviewDTO reviewDTO);
    void deleteReview(int reviewId);
}
