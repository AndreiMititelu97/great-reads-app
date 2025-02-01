package org.greatreads.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.greatreads.dto.ReviewDTO;
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

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;

    @Override
    @Transactional
    public Review addReview(int bookId, ReviewDTO reviewDTO) {
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new BookNotFoundException(bookId));

        User user = userRepository.findById(reviewDTO.getUserId())
                .orElseThrow(() -> new UserNotFoundException(reviewDTO.getUserId()));

        Review review = new Review();
        review.setRating(reviewDTO.getRating());
        review.setComment(reviewDTO.getComment());
        review.setBook(book);
        review.setUser(user);

        return reviewRepository.save(review);
    }

    @Override
    @Transactional
    public Review updateReview(int reviewId, ReviewDTO reviewDTO) {
        Review review = reviewRepository.findById(reviewId).orElseThrow(() -> new ReviewNotFoundException(reviewId));

        if (!Objects.equals(review.getUser().getId(), reviewDTO.getUserId())) {
            throw new IllegalArgumentException("This user is not allowed to update this review.");
        }

        review.setRating(reviewDTO.getRating());
        review.setComment(reviewDTO.getComment());
        return reviewRepository.save(review);
    }

    @Override
    @Transactional
    public void deleteReview(int reviewId) {
        Review review = reviewRepository.findById(reviewId).orElseThrow(() -> new ReviewNotFoundException(reviewId));
        reviewRepository.delete(review);
    }
}
