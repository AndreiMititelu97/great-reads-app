package org.goodreads.service;

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
import org.greatreads.service.impl.ReviewServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDateTime;
import java.util.List;
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
        reviewDTO.setBookId(book.getId());
        reviewDTO.setUserId(2);
        reviewDTO.setRating(1);
        reviewDTO.setComment("myComment");

        Mockito.when(bookRepository.findById(reviewDTO.getBookId())).thenReturn(Optional.of(book));
        Mockito.when(userRepository.findById(reviewDTO.getUserId())).thenReturn(Optional.of(user));
        Mockito.when(reviewRepository.save(Mockito.any(Review.class))).thenAnswer(invocation -> invocation.getArgument(0));

        ReviewResponseDTO review = reviewService.addReview(reviewDTO);

        Assertions.assertNotNull(review);
        Assertions.assertEquals(reviewDTO.getRating(), review.getRating());
        Assertions.assertEquals(reviewDTO.getComment(), review.getComment());
        Assertions.assertEquals(user.getId(), review.getUserId());
        Assertions.assertEquals(book.getId(), review.getBookId());
    }

    @Test
    void testAddReview_BookNotFound() {
        ReviewDTO reviewDTO = new ReviewDTO();
        reviewDTO.setBookId(1);

        Mockito.when(bookRepository.findById(reviewDTO.getBookId()))
                .thenThrow(new BookNotFoundException(reviewDTO.getBookId()));

        assertThrows(BookNotFoundException.class, () -> reviewService.addReview(reviewDTO));
    }

    @Test
    void testAddReview_UserNotFound() {
        Book book = new Book();
        ReviewDTO reviewDTO = new ReviewDTO();
        reviewDTO.setUserId(1);
        reviewDTO.setBookId(2);

        Mockito.when(bookRepository.findById(reviewDTO.getBookId())).thenReturn(Optional.of(book));
        Mockito.when(userRepository.findById(reviewDTO.getUserId()))
                .thenThrow(new UserNotFoundException(reviewDTO.getUserId()));

        assertThrows(UserNotFoundException.class, () -> reviewService.addReview(reviewDTO));
    }

    @Test
    void testUpdateReview() {
        User user = new User();
        user.setId(25);

        Book book = new Book();
        book.setId(1);

        Review review = new Review();
        review.setId(1);
        review.setUser(user);
        review.setRating(1);
        review.setComment("oldComment");
        review.setBook(book);

        UpdateReviewDTO updateReviewDTO = new UpdateReviewDTO();
        updateReviewDTO.setUserId(25);
        updateReviewDTO.setRating(5);
        updateReviewDTO.setComment("newComment");

        Mockito.when(reviewRepository.findById(review.getId())).thenReturn(Optional.of(review));
        Mockito.when(reviewRepository.save(Mockito.any(Review.class))).thenAnswer(invocation -> invocation.getArgument(0));

        ReviewResponseDTO updatedReview = reviewService.updateReview(review.getId(), updateReviewDTO);

        Assertions.assertNotNull(updatedReview);
        Assertions.assertEquals(updateReviewDTO.getRating(), updatedReview.getRating());
        Assertions.assertEquals(updateReviewDTO.getComment(), updatedReview.getComment());
        Assertions.assertEquals(user.getId(), updatedReview.getUserId());
    }

    @Test
    void testUpdateReview_ReviewNotFound() {
        UpdateReviewDTO updateReviewDTO = new UpdateReviewDTO();
        int reviewId = 1;

        Mockito.when(reviewRepository.findById(reviewId)).thenThrow(new ReviewNotFoundException(reviewId));
        assertThrows(ReviewNotFoundException.class, () -> reviewService.updateReview(reviewId, updateReviewDTO));
    }

    @Test
    void testUpdateReview_UserNotAllowed() {
        UpdateReviewDTO updateReviewDTO = new UpdateReviewDTO();
        updateReviewDTO.setUserId(2);

        User user = new User();
        user.setId(25);

        Review review = new Review();
        review.setId(1);
        review.setUser(user);
        int reviewId = 1;

        Mockito.when(reviewRepository.findById(review.getId())).thenReturn(Optional.of(review));
        assertThrows(IllegalArgumentException.class, () -> reviewService.updateReview(reviewId, updateReviewDTO));
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

    @Test
    void testGetReview() {
        User user = new User();
        user.setId(25);

        Book book = new Book();
        book.setId(1);

        Review review = new Review();
        review.setId(1);
        review.setUser(user);
        review.setBook(book);
        review.setRating(2);
        review.setComment("myComment");
        review.setPublishedDate(LocalDateTime.now());
        review.setCreatedAt(LocalDateTime.now());
        review.setUpdatedAt(LocalDateTime.now());

        Mockito.when(reviewRepository.findById(review.getId())).thenReturn(Optional.of(review));
        ReviewResponseDTO reviewResponseDTO = reviewService.getReview(review.getId());

        Assertions.assertNotNull(reviewResponseDTO);
        Assertions.assertEquals(review.getId(), reviewResponseDTO.getId());
        Assertions.assertEquals(review.getUser().getId(), reviewResponseDTO.getUserId());
        Assertions.assertEquals(review.getBook().getId(), reviewResponseDTO.getBookId());
        Assertions.assertEquals(review.getRating(), reviewResponseDTO.getRating());
        Assertions.assertEquals(review.getComment(), reviewResponseDTO.getComment());
        Assertions.assertEquals(review.getPublishedDate(), reviewResponseDTO.getPublishedDate());
        Assertions.assertEquals(review.getCreatedAt(), reviewResponseDTO.getCreatedAt());
        Assertions.assertEquals(review.getUpdatedAt(), reviewResponseDTO.getUpdatedAt());
    }

    @Test
    void testGetReview_ReviewNotFound() {
        int reviewId = 1;
        Mockito.when(reviewRepository.findById(reviewId)).thenThrow(new ReviewNotFoundException(reviewId));
        assertThrows(ReviewNotFoundException.class, () -> reviewService.getReview(reviewId));
    }

    @Test
    void testGetAllReviewsByBookId() {
        User user = new User();
        user.setId(25);
        User user2 = new User();
        user2.setId(21);

        Book book = new Book();
        book.setId(1);

        Review review = new Review();
        review.setId(1);
        review.setUser(user);
        review.setBook(book);
        review.setRating(2);
        review.setComment("myComment");
        review.setPublishedDate(LocalDateTime.now());
        review.setCreatedAt(LocalDateTime.now());
        review.setUpdatedAt(LocalDateTime.now());

        Review review2 = new Review();
        review2.setId(2);
        review2.setUser(user2);
        review2.setBook(book);
        review2.setRating(5);
        review2.setComment("myComment2");
        review2.setPublishedDate(LocalDateTime.now());
        review2.setCreatedAt(LocalDateTime.now());
        review2.setUpdatedAt(LocalDateTime.now());

        List<Review> reviews = List.of(review, review2);
        Mockito.when(reviewRepository.findAllByBook_Id(book.getId())).thenReturn(reviews);
        List<ReviewResponseDTO> reviewResponseDTOs = reviewService.getAllReviewsByBookId(book.getId());

        Assertions.assertNotNull(reviewResponseDTOs);
        Assertions.assertEquals(reviews.size(), reviewResponseDTOs.size());
    }

    @Test
    void testGetAllReviewsByBookId_EmptyList() {
        Book book = new Book();
        book.setId(1);

        Mockito.when(reviewRepository.findAllByBook_Id(book.getId())).thenReturn(List.of());
        List<ReviewResponseDTO> reviewResponseDTOs = reviewService.getAllReviewsByBookId(book.getId());

        Assertions.assertEquals(List.of(), reviewResponseDTOs);
    }

    @Test
    void testGetAllReviewsByUserId() {
        User user = new User();
        user.setId(25);

        Book book = new Book();
        book.setId(1);

        Book book2 = new Book();
        book2.setId(2);

        Review review = new Review();
        review.setId(1);
        review.setUser(user);
        review.setBook(book);
        review.setRating(2);
        review.setComment("myComment");
        review.setPublishedDate(LocalDateTime.now());
        review.setCreatedAt(LocalDateTime.now());
        review.setUpdatedAt(LocalDateTime.now());

        Review review2 = new Review();
        review2.setId(2);
        review2.setUser(user);
        review2.setBook(book2);
        review2.setRating(5);
        review2.setComment("myComment2");
        review2.setPublishedDate(LocalDateTime.now());
        review2.setCreatedAt(LocalDateTime.now());
        review2.setUpdatedAt(LocalDateTime.now());

        List<Review> reviews = List.of(review, review2);
        Mockito.when(reviewRepository.findAllByUser_id(user.getId())).thenReturn(reviews);
        List<ReviewResponseDTO> reviewResponseDTOs = reviewService.getAllReviewsByUserId(user.getId());

        Assertions.assertNotNull(reviewResponseDTOs);
        Assertions.assertEquals(reviews.size(), reviewResponseDTOs.size());
    }

    @Test
    void testGetAllReviewsByUserId_EmptyList() {
        User user = new User();
        user.setId(25);

        Mockito.when(reviewRepository.findAllByUser_id(user.getId())).thenReturn(List.of());
        List<ReviewResponseDTO> reviewResponseDTOs = reviewService.getAllReviewsByUserId(user.getId());

        Assertions.assertEquals(List.of(), reviewResponseDTOs);
    }

    @Test
    void testGetAverageRatingForBook() {
        User user = new User();
        user.setId(25);
        User user2 = new User();
        user2.setId(21);

        Book book = new Book();
        book.setId(1);

        Review review = new Review();
        review.setId(1);
        review.setUser(user);
        review.setBook(book);
        review.setRating(2);
        review.setComment("myComment");
        review.setPublishedDate(LocalDateTime.now());
        review.setCreatedAt(LocalDateTime.now());
        review.setUpdatedAt(LocalDateTime.now());

        Review review2 = new Review();
        review2.setId(2);
        review2.setUser(user2);
        review2.setBook(book);
        review2.setRating(5);
        review2.setComment("myComment2");
        review2.setPublishedDate(LocalDateTime.now());
        review2.setCreatedAt(LocalDateTime.now());
        review2.setUpdatedAt(LocalDateTime.now());

        List<Review> reviews = List.of(review, review2);
        Mockito.when(reviewRepository.findAllByBook_Id(book.getId())).thenReturn(reviews);

        double expectedResult = (double) (review.getRating() + review2.getRating()) / reviews.size();
        double result = reviewService.getAverageRatingForBook(book.getId());

        Assertions.assertEquals(expectedResult, result, 0.001);
    }

    @Test
    void testGetAverageRatingForBook_NoRating() {
        int bookId = 1;
        Mockito.when(reviewRepository.findAllByBook_Id(bookId)).thenReturn(List.of());

        double expectedResult = 0.0;
        double result = reviewService.getAverageRatingForBook(bookId);

        Assertions.assertEquals(expectedResult, result, 0.001);
    }
}
