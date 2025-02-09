package org.goodreads.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.greatreads.dto.book.BookResponseDTO;
import org.greatreads.dto.genre.GenreResponseDTO;
import org.greatreads.dto.user.UserSimpleResponseDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Set;

class BookResponseDTOTest {
    private static final int ID = 1;
    private static final String TITLE = "title";
    private static final String DESCRIPTION = "description";
    private static final GenreResponseDTO GENRE = new GenreResponseDTO();
    private static final String ISBN = "isbn";
    private static final UserSimpleResponseDTO USER = new UserSimpleResponseDTO();
    private static final LocalDate PUBLISH_DATE = LocalDate.now();
    private static final String URL_LINK = "link.com";
    private static final boolean IS_APPROVED = true;
    private static final int NUMBER_OF_REVIEWS = 2;
    private static final double RATING = 1.5;

    private Validator validator;
    private BookResponseDTO bookResponseDTO;

    @BeforeEach
    void setUp() {
        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            validator = factory.getValidator();
        }
        bookResponseDTO = new BookResponseDTO();
    }

    @Test
    void testValidReviewResponseDTO() {
        bookResponseDTO.setId(ID);
        bookResponseDTO.setTitle(TITLE);
        bookResponseDTO.setDescription(DESCRIPTION);
        bookResponseDTO.setGenre(GENRE);
        bookResponseDTO.setIsbn(ISBN);
        bookResponseDTO.setAuthor(USER);
        bookResponseDTO.setPublishDate(PUBLISH_DATE);
        bookResponseDTO.setRating(RATING);
        bookResponseDTO.setNumberOfReviews(NUMBER_OF_REVIEWS);
        bookResponseDTO.setUrlLink(URL_LINK);
        bookResponseDTO.setApproved(IS_APPROVED);

        Set<ConstraintViolation<BookResponseDTO>> constraintViolations = validator.validate(bookResponseDTO);
        Assertions.assertTrue(constraintViolations.isEmpty());
    }

    @Test
    void testNullReviewResponseDTO() {
        Set<ConstraintViolation<BookResponseDTO>> constraintViolations = validator.validate(bookResponseDTO);
        Assertions.assertTrue(constraintViolations.isEmpty());
    }
}
