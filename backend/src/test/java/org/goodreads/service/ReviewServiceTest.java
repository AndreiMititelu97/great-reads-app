package org.goodreads.service;

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
import org.greatreads.service.impl.ReviewServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class ReviewServiceTest {
    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private ReviewServiceImpl reviewService;

    @Test
    void testAddReview() {
        Book book = new Book();
        book.setId(1);

        User user = new User();
        user.setId(2);

        ReviewDTO reviewDTO = new ReviewDTO();
        reviewDTO.setUserId(2);
        reviewDTO.setRating(1);
        reviewDTO.setComment("myComment");

        Mockito.when(bookRepository.findById(book.getId())).thenReturn(Optional.of(book));
        Mockito.when(userRepository.findById(reviewDTO.getUserId())).thenReturn(Optional.of(user));
        Mockito.when(reviewRepository.save(Mockito.any(Review.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Review review = reviewService.addReview(book.getId(), reviewDTO);

        Assertions.assertNotNull(review);
        Assertions.assertEquals(reviewDTO.getRating(), review.getRating());
        Assertions.assertEquals(reviewDTO.getComment(), review.getComment());
        Assertions.assertEquals(user, review.getUser());
        Assertions.assertEquals(book, review.getBook());
    }

    @Test
    void testAddReview_BookNotFound() {
        ReviewDTO reviewDTO = new ReviewDTO();
        int bookId = 1;
        Mockito.when(bookRepository.findById(bookId)).thenThrow(new BookNotFoundException(bookId));

        assertThrows(BookNotFoundException.class, () -> reviewService.addReview(bookId, reviewDTO));
    }

    @Test
    void testAddReview_UserNotFound() {
        Book book = new Book();
        book.setId(1);
        int bookId = 1;

        ReviewDTO reviewDTO = new ReviewDTO();
        reviewDTO.setUserId(2);

        Mockito.when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
        Mockito.when(userRepository.findById(reviewDTO.getUserId()))
                .thenThrow(new UserNotFoundException(reviewDTO.getUserId()));

        assertThrows(UserNotFoundException.class, () -> reviewService.addReview(bookId, reviewDTO));
    }

    @Test
    void testUpdateReview() {
        User user = new User();
        user.setId(25);

        Review review = new Review();
        review.setId(1);
        review.setUser(user);
        review.setRating(1);
        review.setComment("oldComment");

        ReviewDTO reviewDTO = new ReviewDTO();
        reviewDTO.setUserId(25);
        reviewDTO.setRating(5);
        reviewDTO.setComment("newComment");

        Mockito.when(reviewRepository.findById(review.getId())).thenReturn(Optional.of(review));
        Mockito.when(reviewRepository.save(Mockito.any(Review.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Review updatedReview = reviewService.updateReview(review.getId(), reviewDTO);

        Assertions.assertNotNull(updatedReview);
        Assertions.assertEquals(reviewDTO.getRating(), updatedReview.getRating());
        Assertions.assertEquals(reviewDTO.getComment(), updatedReview.getComment());
        Assertions.assertEquals(user, updatedReview.getUser());
    }

    @Test
    void testUpdateReview_ReviewNotFound() {
        ReviewDTO reviewDTO = new ReviewDTO();
        int reviewId = 1;

        Mockito.when(reviewRepository.findById(reviewId)).thenThrow(new ReviewNotFoundException(reviewId));
        assertThrows(ReviewNotFoundException.class, () -> reviewService.updateReview(reviewId, reviewDTO));
    }

    @Test
    void testUpdateReview_UserNotAllowed() {
        ReviewDTO reviewDTO = new ReviewDTO();
        reviewDTO.setUserId(2);

        User user = new User();
        user.setId(25);

        Review review = new Review();
        review.setId(1);
        review.setUser(user);
        int reviewId = 1;

        Mockito.when(reviewRepository.findById(review.getId())).thenReturn(Optional.of(review));
        assertThrows(IllegalArgumentException.class, () -> reviewService.updateReview(reviewId, reviewDTO));
    }

    @Test
    void testDeleteReview() {
        Review review = new Review();
        review.setId(1);

        Mockito.when(reviewRepository.findById(review.getId())).thenReturn(Optional.of(review));
        reviewService.deleteReview(review.getId());

        Mockito.verify(reviewRepository, Mockito.times(1)).delete(Mockito.any(Review.class));
    }

    @Test
    void testDeleteReview_ReviewNotFoundReview() {
        int reviewId = 1;
        Mockito.when(reviewRepository.findById(reviewId)).thenThrow(new ReviewNotFoundException(reviewId));

        assertThrows(ReviewNotFoundException.class, () -> reviewService.deleteReview(reviewId));
    }
}
