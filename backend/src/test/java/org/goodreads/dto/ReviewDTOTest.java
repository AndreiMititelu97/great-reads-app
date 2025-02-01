package org.goodreads.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.greatreads.dto.ReviewDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

class ReviewDTOTest {
    private final int validRating = 1;
    private final String comment = "comment";
    private final int userId = 1;
    private final int bookid = 2;

    private Validator validator;
    private ReviewDTO reviewDTO;

    @BeforeEach
    void setUp() {
        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            validator = factory.getValidator();
        }
        reviewDTO = new ReviewDTO();
    }

    @Test
    void testValidReviewDTO() {
        reviewDTO.setRating(validRating);
        reviewDTO.setComment(comment);
        reviewDTO.setUserId(userId);
        reviewDTO.setBookId(bookid);

        Set<ConstraintViolation<ReviewDTO>> constraintViolations = validator.validate(reviewDTO);
        Assertions.assertTrue(constraintViolations.isEmpty());
    }

    @Test
    void testInvalidRating() {
        reviewDTO.setRating(0);
        reviewDTO.setComment(comment);
        reviewDTO.setUserId(userId);
        reviewDTO.setBookId(bookid);

        Set<ConstraintViolation<ReviewDTO>> constraintViolations = validator.validate(reviewDTO);
        Assertions.assertEquals(1, constraintViolations.size());
    }

    @Test
    void testNullRating() {
        reviewDTO.setRating(null);
        reviewDTO.setComment(comment);
        reviewDTO.setUserId(userId);
        reviewDTO.setBookId(bookid);

        Set<ConstraintViolation<ReviewDTO>> constraintViolations = validator.validate(reviewDTO);
        Assertions.assertEquals(1, constraintViolations.size());
    }

    @Test
    void testMaxCommentLength() {
        reviewDTO.setRating(validRating);
        reviewDTO.setUserId(userId);
        reviewDTO.setBookId(bookid);
        reviewDTO.setComment(" ".repeat(510));

        Set<ConstraintViolation<ReviewDTO>> constraintViolations = validator.validate(reviewDTO);
        Assertions.assertEquals(1, constraintViolations.size());
    }

    @Test
    void testNullUserId() {
        reviewDTO.setRating(validRating);
        reviewDTO.setComment(comment);
        reviewDTO.setUserId(null);
        reviewDTO.setBookId(bookid);

        Set<ConstraintViolation<ReviewDTO>> constraintViolations = validator.validate(reviewDTO);
        Assertions.assertEquals(1, constraintViolations.size());
    }

    @Test
    void testNullBookId() {
        reviewDTO.setRating(validRating);
        reviewDTO.setComment(comment);
        reviewDTO.setUserId(userId);
        reviewDTO.setBookId(null);

        Set<ConstraintViolation<ReviewDTO>> constraintViolations = validator.validate(reviewDTO);
        Assertions.assertEquals(1, constraintViolations.size());
    }
}
