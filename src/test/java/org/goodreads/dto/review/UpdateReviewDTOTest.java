package org.goodreads.dto.review;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.greatreads.dto.review.UpdateReviewDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

class UpdateReviewDTOTest {
    private static final int VALID_RATING = 1;
    private static final String COMMENT = "comment";
    private static final int USER_ID = 1;

    private Validator validator;
    private UpdateReviewDTO updatedReviewDTO;

    @BeforeEach
    void setUp() {
        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            validator = factory.getValidator();
        }
        updatedReviewDTO = new UpdateReviewDTO();
    }

    @Test
    void testValidUpdateReviewDTO() {
        updatedReviewDTO.setUserId(USER_ID);
        updatedReviewDTO.setRating(VALID_RATING);
        updatedReviewDTO.setComment(COMMENT);

        Set<ConstraintViolation<UpdateReviewDTO>> constraintViolations = validator.validate(updatedReviewDTO);
        Assertions.assertTrue(constraintViolations.isEmpty());
    }

    @Test
    void testNullRatingAndComment() {
        updatedReviewDTO.setUserId(USER_ID);
        updatedReviewDTO.setRating(null);
        updatedReviewDTO.setComment(null);

        Set<ConstraintViolation<UpdateReviewDTO>> constraintViolations = validator.validate(updatedReviewDTO);
        Assertions.assertTrue(constraintViolations.isEmpty());
    }

    @Test
    void testNullUserId() {
        updatedReviewDTO.setUserId(null);
        updatedReviewDTO.setRating(VALID_RATING);
        updatedReviewDTO.setComment(COMMENT);

        Set<ConstraintViolation<UpdateReviewDTO>> constraintViolations = validator.validate(updatedReviewDTO);
        Assertions.assertEquals(1, constraintViolations.size());
    }
}
