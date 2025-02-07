package org.goodreads.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.greatreads.dto.BookDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Set;

class BookDTOTest {

    private static final String ISBN = "1234567890";
    private static final String TITLE = "title";
    private static final Integer GENRE_ID = 1;
    private static final Integer AUTHOR_ID = 2;
    private static final LocalDateTime PUBLISHED_DATE = LocalDateTime.now();

    private BookDTO bookDTO;
    private Validator validator;

    @BeforeEach
    void setUp() {
        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            validator = factory.getValidator();
        }
        bookDTO = new BookDTO();
    }

    @Test
    void testValidBookDTO() {
        bookDTO.setTitle(TITLE);
        bookDTO.setIsbn(ISBN);
        bookDTO.setGenreId(GENRE_ID);
        bookDTO.setAuthorId(AUTHOR_ID);
        bookDTO.setPublishDate(PUBLISHED_DATE);

        Set<ConstraintViolation<BookDTO>> constraintViolations = validator.validate(bookDTO);
        Assertions.assertTrue(constraintViolations.isEmpty());
    }

    @Test
    void testNullTitle() {
        bookDTO.setTitle(null);
        bookDTO.setIsbn(ISBN);
        bookDTO.setGenreId(GENRE_ID);
        bookDTO.setAuthorId(AUTHOR_ID);
        bookDTO.setPublishDate(PUBLISHED_DATE);

        Set<ConstraintViolation<BookDTO>> constraintViolations = validator.validate(bookDTO);
        Assertions.assertEquals(1, constraintViolations.size());
    }

    @Test
    void testBlankTitle() {
        bookDTO.setTitle("");
        bookDTO.setIsbn(ISBN);
        bookDTO.setGenreId(GENRE_ID);
        bookDTO.setAuthorId(AUTHOR_ID);
        bookDTO.setPublishDate(PUBLISHED_DATE);

        Set<ConstraintViolation<BookDTO>> constraintViolations = validator.validate(bookDTO);
        Assertions.assertEquals(1, constraintViolations.size());
    }

    @Test
    void testMaxTitleLength() {
        bookDTO.setTitle("a".repeat(256));
        bookDTO.setIsbn(ISBN);
        bookDTO.setGenreId(GENRE_ID);
        bookDTO.setAuthorId(AUTHOR_ID);
        bookDTO.setPublishDate(PUBLISHED_DATE);

        Set<ConstraintViolation<BookDTO>> constraintViolations = validator.validate(bookDTO);
        Assertions.assertEquals(1, constraintViolations.size());
    }

    @Test
    void testMaxDescription() {
        bookDTO.setTitle(TITLE);
        bookDTO.setIsbn(ISBN);
        bookDTO.setGenreId(GENRE_ID);
        bookDTO.setAuthorId(AUTHOR_ID);
        bookDTO.setPublishDate(PUBLISHED_DATE);
        bookDTO.setDescription("a".repeat(2001));

        Set<ConstraintViolation<BookDTO>> constraintViolations = validator.validate(bookDTO);
        Assertions.assertEquals(1, constraintViolations.size());
    }

    @Test
    void testNullGenreId() {
        bookDTO.setTitle(TITLE);
        bookDTO.setIsbn(ISBN);
        bookDTO.setGenreId(null);
        bookDTO.setAuthorId(AUTHOR_ID);
        bookDTO.setPublishDate(PUBLISHED_DATE);

        Set<ConstraintViolation<BookDTO>> constraintViolations = validator.validate(bookDTO);
        Assertions.assertEquals(1, constraintViolations.size());
    }

    @Test
    void testNullAuthorId() {
        bookDTO.setTitle(TITLE);
        bookDTO.setIsbn(ISBN);
        bookDTO.setGenreId(GENRE_ID);
        bookDTO.setAuthorId(null);
        bookDTO.setPublishDate(PUBLISHED_DATE);

        Set<ConstraintViolation<BookDTO>> constraintViolations = validator.validate(bookDTO);
        Assertions.assertEquals(1, constraintViolations.size());
    }

    @Test
    void testNullPublishedDate() {
        bookDTO.setTitle(TITLE);
        bookDTO.setIsbn(ISBN);
        bookDTO.setGenreId(GENRE_ID);
        bookDTO.setAuthorId(AUTHOR_ID);
        bookDTO.setPublishDate(null);

        Set<ConstraintViolation<BookDTO>> constraintViolations = validator.validate(bookDTO);
        Assertions.assertEquals(1, constraintViolations.size());
    }

    @Test
    void testNullIsbn() {
        bookDTO.setTitle(TITLE);
        bookDTO.setIsbn(null);
        bookDTO.setGenreId(GENRE_ID);
        bookDTO.setAuthorId(AUTHOR_ID);
        bookDTO.setPublishDate(PUBLISHED_DATE);

        Set<ConstraintViolation<BookDTO>> constraintViolations = validator.validate(bookDTO);
        Assertions.assertEquals(1, constraintViolations.size());
    }

    @Test
    void testBlankIsbn() {
        bookDTO.setTitle(TITLE);
        bookDTO.setIsbn("");
        bookDTO.setGenreId(GENRE_ID);
        bookDTO.setAuthorId(AUTHOR_ID);
        bookDTO.setPublishDate(PUBLISHED_DATE);

        Set<ConstraintViolation<BookDTO>> constraintViolations = validator.validate(bookDTO);
        Assertions.assertEquals(1, constraintViolations.size());
    }
}
