package org.goodreads.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.greatreads.dto.user.UserSimpleResponseDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

class UserSimpleResponseDTOTest {
    private static final int ID = 1;
    private static final String EMAIL = "test@email.com";
    private static final String FIRST_NAME = "first name";
    private static final String LAST_NAME = "last name";
    private static final String AVATAR = "avatar.png";

    private Validator validator;
    private UserSimpleResponseDTO userSimpleResponseDTO;

    @BeforeEach
    void setUp() {
        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            validator = factory.getValidator();
        }
        userSimpleResponseDTO = new UserSimpleResponseDTO();
    }

    @Test
    void testValidReviewResponseDTO() {
        userSimpleResponseDTO.setId(ID);
        userSimpleResponseDTO.setEmail(EMAIL);
        userSimpleResponseDTO.setFirstName(FIRST_NAME);
        userSimpleResponseDTO.setLastName(LAST_NAME);
        userSimpleResponseDTO.setAvatar(AVATAR);

        Set<ConstraintViolation<UserSimpleResponseDTO>> constraintViolations = validator.validate(userSimpleResponseDTO);
        Assertions.assertTrue(constraintViolations.isEmpty());
    }

    @Test
    void testNullReviewResponseDTO() {
        Set<ConstraintViolation<UserSimpleResponseDTO>> constraintViolations = validator.validate(userSimpleResponseDTO);
        Assertions.assertTrue(constraintViolations.isEmpty());
    }
}
