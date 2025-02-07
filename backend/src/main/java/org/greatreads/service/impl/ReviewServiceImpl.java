package org.greatreads.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.greatreads.dto.review.ReviewDTO;
import org.greatreads.dto.review.ReviewResponseDTO;
import org.greatreads.dto.review.UpdateReviewDTO;
import org.greatreads.exception.BookNotFoundException;
import org.greatreads.exception.ReviewNotFoundException;
import org.greatreads.exception.UserNotFoundException;
import org.greatreads.model.Book;
import org.greatreads.model.Review;
import org.greatreads.model.User;
import org.greatreads.repository.BookRepository;
import org.greatreads.repository.ReviewRepository;
import org.greatreads.repository.UserRepository;
import org.greatreads.service.ReviewService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;

    @Override
    @Transactional
    public ReviewResponseDTO addReview(ReviewDTO reviewDTO) {
        Book book = bookRepository.findById(reviewDTO.getBookId())
                .orElseThrow(() -> new BookNotFoundException(reviewDTO.getBookId()));
        User user = userRepository.findById(reviewDTO.getUserId())
                .orElseThrow(() -> new UserNotFoundException(reviewDTO.getUserId()));

        Review review = new Review();
        review.setRating(reviewDTO.getRating());
        review.setComment(reviewDTO.getComment());
        review.setBook(book);
        review.setUser(user);
        reviewRepository.save(review);

        return reviewToReviewResponseDto(review);
    }

    @Override
    public ReviewResponseDTO getReview(int reviewId) {
        Review review =  reviewRepository.findById(reviewId).orElseThrow(() -> new ReviewNotFoundException(reviewId));
        return reviewToReviewResponseDto(review);
    }

    @Override
    @Transactional
    public ReviewResponseDTO updateReview(int reviewId, UpdateReviewDTO updateReviewDTO) {
        Review review = reviewRepository.findById(reviewId).orElseThrow(() -> new ReviewNotFoundException(reviewId));

        if (!Objects.equals(review.getUser().getId(), updateReviewDTO.getUserId())) {
            throw new IllegalArgumentException("This user is not allowed to update this review.");
        }

        if (updateReviewDTO.getRating() != null) {
            review.setRating(updateReviewDTO.getRating());
        }
        if (updateReviewDTO.getComment() != null) {
            review.setComment(updateReviewDTO.getComment());
        }

        reviewRepository.save(review);
        return reviewToReviewResponseDto(review);
    }

    @Override
    @Transactional
    public void deleteReview(int reviewId) {
        Review review = reviewRepository.findById(reviewId).orElseThrow(() -> new ReviewNotFoundException(reviewId));
        reviewRepository.delete(review);
    }

    @Override
    public List<ReviewResponseDTO> getAllReviewsByBookId(int bookId) {
        List<Review> reviews = reviewRepository.findAllByBook_Id(bookId);

        List <ReviewResponseDTO> reviewResponseDTOS = new ArrayList<>();
        for (Review review : reviews) {
            reviewResponseDTOS.add(reviewToReviewResponseDto(review));
        }
        return reviewResponseDTOS;
    }

    @Override
    public List<ReviewResponseDTO> getAllReviewsByUserId(int userId) {
        List<Review> reviews = reviewRepository.findAllByUser_id(userId);

        List <ReviewResponseDTO> reviewResponseDTOS = new ArrayList<>();
        for (Review review : reviews) {
            reviewResponseDTOS.add(reviewToReviewResponseDto(review));
        }
        return reviewResponseDTOS;
    }

    @Override
    public double getAverageRatingForBook(int bookId) {
        List <ReviewResponseDTO> reviews = getAllReviewsByBookId(bookId);
        return calculateAverageRating(reviews);
    }

    private ReviewResponseDTO reviewToReviewResponseDto(Review review) {
        ReviewResponseDTO reviewResponseDTO = new ReviewResponseDTO();
        reviewResponseDTO.setId(review.getId());
        reviewResponseDTO.setUserId(review.getUser().getId());
        reviewResponseDTO.setBookId(review.getBook().getId());
        reviewResponseDTO.setRating(review.getRating());
        reviewResponseDTO.setComment(review.getComment());
        reviewResponseDTO.setPublishedDate(review.getPublishedDate());
        reviewResponseDTO.setCreatedAt(review.getCreatedAt());
        reviewResponseDTO.setUpdatedAt(review.getUpdatedAt());

        return reviewResponseDTO;
    }

    private double calculateAverageRating(List<ReviewResponseDTO> reviews) {
        if (reviews.isEmpty()) {
            return 0.0;
        }

        int length = reviews.size();
        int sum = 0;
        for (ReviewResponseDTO reviewResponseDTO : reviews) {
            sum += reviewResponseDTO.getRating();
        }
        return (double) sum / length;
    }
}
