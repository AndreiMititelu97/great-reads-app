package org.goodreads.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.greatreads.dto.UserDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

class UserDTOTest {
    private static final String VALID_EMAIL = "valid@email.com";
    private static final String VALID_PASSWORD = "ag5!Wc1I*Kq2";
    private static final String FIRST_NAME = "John";
    private static final String LAST_NAME = "Wick";
    private static final int ROLE_ID = 1;

    private Validator validator;
    private UserDTO userDTO;

    @BeforeEach
    void setUp() {
        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            validator = factory.getValidator();
        }
        userDTO = new UserDTO();
    }

    @Test
    void testValidUserDTO() {
        userDTO.setEmail(VALID_EMAIL);
        userDTO.setPassword(VALID_PASSWORD);
        userDTO.setFirstName(FIRST_NAME);
        userDTO.setLastName(LAST_NAME);
        userDTO.setRoleId(ROLE_ID);

        Set<ConstraintViolation<UserDTO>> constraintViolations = validator.validate(userDTO);
        Assertions.assertTrue(constraintViolations.isEmpty());
    }

    @Test
    void testInvalidEmail() {
        String invalidEmail = "invalidEmail";
        userDTO.setEmail(invalidEmail);
        userDTO.setPassword(VALID_PASSWORD);
        userDTO.setFirstName(FIRST_NAME);
        userDTO.setLastName(LAST_NAME);
        userDTO.setRoleId(ROLE_ID);

        Set<ConstraintViolation<UserDTO>> constraintViolations = validator.validate(userDTO);
        Assertions.assertEquals(1, constraintViolations.size());
    }

    @Test
    void testBlankEmail() {
        userDTO.setPassword(VALID_PASSWORD);
        userDTO.setFirstName(FIRST_NAME);
        userDTO.setLastName(LAST_NAME);
        userDTO.setRoleId(ROLE_ID);

        Set<ConstraintViolation<UserDTO>> constraintViolations = validator.validate(userDTO);
        Assertions.assertEquals(1, constraintViolations.size());
    }

    @Test
    void testNullEmail() {
        userDTO.setEmail(null);
        userDTO.setPassword(VALID_PASSWORD);
        userDTO.setFirstName(FIRST_NAME);
        userDTO.setLastName(LAST_NAME);
        userDTO.setRoleId(ROLE_ID);

        Set<ConstraintViolation<UserDTO>> constraintViolations = validator.validate(userDTO);
        Assertions.assertEquals(1, constraintViolations.size());
    }

    @Test
    void testInvalidPassword() {
        String invalidPassword = "123";
        userDTO.setEmail(VALID_EMAIL);
        userDTO.setPassword(invalidPassword);
        userDTO.setFirstName(FIRST_NAME);
        userDTO.setLastName(LAST_NAME);
        userDTO.setRoleId(ROLE_ID);

        Set<ConstraintViolation<UserDTO>> constraintViolations = validator.validate(userDTO);
        Assertions.assertEquals(1, constraintViolations.size());
    }

    @Test
    void testBlankPassword() {
        userDTO.setEmail(VALID_EMAIL);
        userDTO.setFirstName(FIRST_NAME);
        userDTO.setLastName(LAST_NAME);
        userDTO.setRoleId(ROLE_ID);

        Set<ConstraintViolation<UserDTO>> constraintViolations = validator.validate(userDTO);
        Assertions.assertEquals(1, constraintViolations.size());
    }

    @Test
    void testNullPassword() {
        userDTO.setEmail(VALID_EMAIL);
        userDTO.setPassword(null);
        userDTO.setFirstName(FIRST_NAME);
        userDTO.setLastName(LAST_NAME);
        userDTO.setRoleId(ROLE_ID);

        Set<ConstraintViolation<UserDTO>> constraintViolations = validator.validate(userDTO);
        Assertions.assertEquals(1, constraintViolations.size());
    }

    @Test
    void testNullFirstName() {
        userDTO.setEmail(VALID_EMAIL);
        userDTO.setPassword(VALID_PASSWORD);
        userDTO.setFirstName(null);
        userDTO.setLastName(LAST_NAME);
        userDTO.setRoleId(ROLE_ID);

        Set<ConstraintViolation<UserDTO>> constraintViolations = validator.validate(userDTO);
        Assertions.assertEquals(1, constraintViolations.size());
    }

    @Test
    void testBlankFirstName() {
        userDTO.setEmail(VALID_EMAIL);
        userDTO.setPassword(VALID_PASSWORD);
        userDTO.setFirstName("");
        userDTO.setLastName(LAST_NAME);
        userDTO.setRoleId(ROLE_ID);

        Set<ConstraintViolation<UserDTO>> constraintViolations = validator.validate(userDTO);
        Assertions.assertEquals(1, constraintViolations.size());
    }

    @Test
    void testNullLastName() {
        userDTO.setEmail(VALID_EMAIL);
        userDTO.setPassword(VALID_PASSWORD);
        userDTO.setFirstName(FIRST_NAME);
        userDTO.setLastName(null);
        userDTO.setRoleId(ROLE_ID);

        Set<ConstraintViolation<UserDTO>> constraintViolations = validator.validate(userDTO);
        Assertions.assertEquals(1, constraintViolations.size());
    }

    @Test
    void testBlankLastName() {
        userDTO.setEmail(VALID_EMAIL);
        userDTO.setPassword(VALID_PASSWORD);
        userDTO.setFirstName(FIRST_NAME);
        userDTO.setLastName("");
        userDTO.setRoleId(ROLE_ID);

        Set<ConstraintViolation<UserDTO>> constraintViolations = validator.validate(userDTO);
        Assertions.assertEquals(1, constraintViolations.size());
    }

    @Test
    void testNegativeRoleId() {
        userDTO.setEmail(VALID_EMAIL);
        userDTO.setPassword(VALID_PASSWORD);
        userDTO.setFirstName(FIRST_NAME);
        userDTO.setLastName(LAST_NAME);
        userDTO.setRoleId(-1);

        Set<ConstraintViolation<UserDTO>> constraintViolations = validator.validate(userDTO);
        Assertions.assertEquals(1, constraintViolations.size());
    }

    @Test
    void testNullRoleId() {
        userDTO.setEmail(VALID_EMAIL);
        userDTO.setPassword(VALID_PASSWORD);
        userDTO.setFirstName(FIRST_NAME);
        userDTO.setLastName(LAST_NAME);
        userDTO.setRoleId(null);

        Set<ConstraintViolation<UserDTO>> constraintViolations = validator.validate(userDTO);
        Assertions.assertEquals(1, constraintViolations.size());
    }
}
