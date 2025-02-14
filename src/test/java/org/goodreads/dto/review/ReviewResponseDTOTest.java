package org.goodreads.dto.review;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.greatreads.dto.review.ReviewResponseDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Set;

class ReviewResponseDTOTest {
    private static final int ID = 1;
    private static final int USER_ID = 2;
    private static final int BOOK_ID = 3;
    private static final int VALID_RATING = 1;
    private static final String COMMENT = "comment";
    private static final LocalDateTime PUBLISHED_DATE = LocalDateTime.now();

    private Validator validator;
    private ReviewResponseDTO reviewResponseDTO;

    @BeforeEach
    void setUp() {
        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            validator = factory.getValidator();
        }
        reviewResponseDTO = new ReviewResponseDTO();
    }

    @Test
    void testValidReviewResponseDTO() {
        reviewResponseDTO.setId(ID);
        reviewResponseDTO.setUserId(USER_ID);
        reviewResponseDTO.setBookId(BOOK_ID);
        reviewResponseDTO.setRating(VALID_RATING);
        reviewResponseDTO.setComment(COMMENT);
        reviewResponseDTO.setPublishedDate(PUBLISHED_DATE);

        Set<ConstraintViolation<ReviewResponseDTO>> constraintViolations = validator.validate(reviewResponseDTO);
        Assertions.assertTrue(constraintViolations.isEmpty());
    }

    @Test
    void testNullReviewResponseDTO() {
        Set<ConstraintViolation<ReviewResponseDTO>> constraintViolations = validator.validate(reviewResponseDTO);
        Assertions.assertTrue(constraintViolations.isEmpty());
    }
}
