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

    private final String isbn = "1234567890";
    private final String title = "title";
    private final Integer genreId = 1;
    private final Integer authorId = 2;
    private final LocalDateTime publishedDate = LocalDateTime.now();

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
        bookDTO.setTitle(title);
        bookDTO.setIsbn(isbn);
        bookDTO.setGenreId(genreId);
        bookDTO.setAuthorId(authorId);
        bookDTO.setPublishDate(publishedDate);

        Set<ConstraintViolation<BookDTO>> constraintViolations = validator.validate(bookDTO);
        Assertions.assertTrue(constraintViolations.isEmpty());
    }

    @Test
    void testNullTitle() {
        bookDTO.setTitle(null);
        bookDTO.setIsbn(isbn);
        bookDTO.setGenreId(genreId);
        bookDTO.setAuthorId(authorId);
        bookDTO.setPublishDate(publishedDate);

        Set<ConstraintViolation<BookDTO>> constraintViolations = validator.validate(bookDTO);
        Assertions.assertEquals(1, constraintViolations.size());
    }

    @Test
    void testBlankTitle() {
        bookDTO.setTitle("");
        bookDTO.setIsbn(isbn);
        bookDTO.setGenreId(genreId);
        bookDTO.setAuthorId(authorId);
        bookDTO.setPublishDate(publishedDate);

        Set<ConstraintViolation<BookDTO>> constraintViolations = validator.validate(bookDTO);
        Assertions.assertEquals(1, constraintViolations.size());
    }

    @Test
    void testMaxTitleLength() {
        bookDTO.setTitle("a".repeat(256));
        bookDTO.setIsbn(isbn);
        bookDTO.setGenreId(genreId);
        bookDTO.setAuthorId(authorId);
        bookDTO.setPublishDate(publishedDate);

        Set<ConstraintViolation<BookDTO>> constraintViolations = validator.validate(bookDTO);
        Assertions.assertEquals(1, constraintViolations.size());
    }

    @Test
    void testMaxDescription() {
        bookDTO.setTitle(title);
        bookDTO.setIsbn(isbn);
        bookDTO.setGenreId(genreId);
        bookDTO.setAuthorId(authorId);
        bookDTO.setPublishDate(publishedDate);
        bookDTO.setDescription("a".repeat(2001));

        Set<ConstraintViolation<BookDTO>> constraintViolations = validator.validate(bookDTO);
        Assertions.assertEquals(1, constraintViolations.size());
    }

    @Test
    void testNullGenreId() {
        bookDTO.setTitle(title);
        bookDTO.setIsbn(isbn);
        bookDTO.setGenreId(null);
        bookDTO.setAuthorId(authorId);
        bookDTO.setPublishDate(publishedDate);

        Set<ConstraintViolation<BookDTO>> constraintViolations = validator.validate(bookDTO);
        Assertions.assertEquals(1, constraintViolations.size());
    }

    @Test
    void testNullAuthorId() {
        bookDTO.setTitle(title);
        bookDTO.setIsbn(isbn);
        bookDTO.setGenreId(genreId);
        bookDTO.setAuthorId(null);
        bookDTO.setPublishDate(publishedDate);

        Set<ConstraintViolation<BookDTO>> constraintViolations = validator.validate(bookDTO);
        Assertions.assertEquals(1, constraintViolations.size());
    }

    @Test
    void testNullPublishedDate() {
        bookDTO.setTitle(title);
        bookDTO.setIsbn(isbn);
        bookDTO.setGenreId(genreId);
        bookDTO.setAuthorId(authorId);
        bookDTO.setPublishDate(null);

        Set<ConstraintViolation<BookDTO>> constraintViolations = validator.validate(bookDTO);
        Assertions.assertEquals(1, constraintViolations.size());
    }

    @Test
    void testNullIsbn() {
        bookDTO.setTitle(title);
        bookDTO.setIsbn(null);
        bookDTO.setGenreId(genreId);
        bookDTO.setAuthorId(authorId);
        bookDTO.setPublishDate(publishedDate);

        Set<ConstraintViolation<BookDTO>> constraintViolations = validator.validate(bookDTO);
        Assertions.assertEquals(1, constraintViolations.size());
    }

    @Test
    void testBlankIsbn() {
        bookDTO.setTitle(title);
        bookDTO.setIsbn("");
        bookDTO.setGenreId(genreId);
        bookDTO.setAuthorId(authorId);
        bookDTO.setPublishDate(publishedDate);

        Set<ConstraintViolation<BookDTO>> constraintViolations = validator.validate(bookDTO);
        Assertions.assertEquals(1, constraintViolations.size());
    }
}
