package org.goodreads.dto.genre;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.greatreads.dto.genre.GenreResponseDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

class GenreResponseDTOTest {
    private static final int ID = 1;
    private static final String NAME = "name";

    private Validator validator;
    private GenreResponseDTO genreResponseDTO;

    @BeforeEach
    void setUp() {
        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            validator = factory.getValidator();
        }
        genreResponseDTO = new GenreResponseDTO();
    }

    @Test
    void testValidReviewResponseDTO() {
        genreResponseDTO.setId(ID);
        genreResponseDTO.setName(NAME);

        Set<ConstraintViolation<GenreResponseDTO>> constraintViolations = validator.validate(genreResponseDTO);
        Assertions.assertTrue(constraintViolations.isEmpty());
    }

    @Test
    void testNullReviewResponseDTO() {
        Set<ConstraintViolation<GenreResponseDTO>> constraintViolations = validator.validate(genreResponseDTO);
        Assertions.assertTrue(constraintViolations.isEmpty());
    }
}
