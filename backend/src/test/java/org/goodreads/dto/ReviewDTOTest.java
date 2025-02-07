package org.goodreads.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.greatreads.dto.review.ReviewDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

class ReviewDTOTest {
    private static final int VALID_RATING = 1;
    private static final String COMMENT = "comment";
    private static final int USER_ID = 1;
    private static final int BOOK_ID = 2;

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
        reviewDTO.setRating(VALID_RATING);
        reviewDTO.setComment(COMMENT);
        reviewDTO.setUserId(USER_ID);
        reviewDTO.setBookId(BOOK_ID);

        Set<ConstraintViolation<ReviewDTO>> constraintViolations = validator.validate(reviewDTO);
        Assertions.assertTrue(constraintViolations.isEmpty());
    }

    @Test
    void testInvalidRating() {
        reviewDTO.setRating(0);
        reviewDTO.setComment(COMMENT);
        reviewDTO.setUserId(USER_ID);
        reviewDTO.setBookId(BOOK_ID);

        Set<ConstraintViolation<ReviewDTO>> constraintViolations = validator.validate(reviewDTO);
        Assertions.assertEquals(1, constraintViolations.size());
    }

    @Test
    void testNullRating() {
        reviewDTO.setRating(null);
        reviewDTO.setComment(COMMENT);
        reviewDTO.setUserId(USER_ID);
        reviewDTO.setBookId(BOOK_ID);

        Set<ConstraintViolation<ReviewDTO>> constraintViolations = validator.validate(reviewDTO);
        Assertions.assertEquals(1, constraintViolations.size());
    }

    @Test
    void testMaxCommentLength() {
        reviewDTO.setRating(VALID_RATING);
        reviewDTO.setUserId(USER_ID);
        reviewDTO.setBookId(BOOK_ID);
        reviewDTO.setComment(" ".repeat(510));

        Set<ConstraintViolation<ReviewDTO>> constraintViolations = validator.validate(reviewDTO);
        Assertions.assertEquals(1, constraintViolations.size());
    }

    @Test
    void testNullUserId() {
        reviewDTO.setRating(VALID_RATING);
        reviewDTO.setComment(COMMENT);
        reviewDTO.setUserId(null);
        reviewDTO.setBookId(BOOK_ID);

        Set<ConstraintViolation<ReviewDTO>> constraintViolations = validator.validate(reviewDTO);
        Assertions.assertEquals(1, constraintViolations.size());
    }

    @Test
    void testNullBookId() {
        reviewDTO.setRating(VALID_RATING);
        reviewDTO.setComment(COMMENT);
        reviewDTO.setUserId(USER_ID);
        reviewDTO.setBookId(null);

        Set<ConstraintViolation<ReviewDTO>> constraintViolations = validator.validate(reviewDTO);
        Assertions.assertEquals(1, constraintViolations.size());
    }
}
