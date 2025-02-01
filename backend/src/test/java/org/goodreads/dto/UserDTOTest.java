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
    private final String validEmail = "valid@email.com";
    private final String validPassword = "ag5!Wc1I*Kq2";
    private final String firstName = "John";
    private final String lastName = "Wick";
    private final int roleId = 1;

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
        userDTO.setEmail(validEmail);
        userDTO.setPassword(validPassword);
        userDTO.setFirstName(firstName);
        userDTO.setLastName(lastName);
        userDTO.setRoleId(roleId);

        Set<ConstraintViolation<UserDTO>> constraintViolations = validator.validate(userDTO);
        Assertions.assertTrue(constraintViolations.isEmpty());
    }

    @Test
    void testInvalidEmail() {
        String invalidEmail = "invalidEmail";
        userDTO.setEmail(invalidEmail);
        userDTO.setPassword(validPassword);
        userDTO.setFirstName(firstName);
        userDTO.setLastName(lastName);
        userDTO.setRoleId(roleId);

        Set<ConstraintViolation<UserDTO>> constraintViolations = validator.validate(userDTO);
        Assertions.assertEquals(1, constraintViolations.size());
    }

    @Test
    void testBlankEmail() {
        userDTO.setPassword(validPassword);
        userDTO.setFirstName(firstName);
        userDTO.setLastName(lastName);
        userDTO.setRoleId(roleId);

        Set<ConstraintViolation<UserDTO>> constraintViolations = validator.validate(userDTO);
        Assertions.assertEquals(1, constraintViolations.size());
    }

    @Test
    void testNullEmail() {
        userDTO.setEmail(null);
        userDTO.setPassword(validPassword);
        userDTO.setFirstName(firstName);
        userDTO.setLastName(lastName);
        userDTO.setRoleId(roleId);

        Set<ConstraintViolation<UserDTO>> constraintViolations = validator.validate(userDTO);
        Assertions.assertEquals(1, constraintViolations.size());
    }

    @Test
    void testInvalidPassword() {
        String invalidPassword = "123";
        userDTO.setEmail(validEmail);
        userDTO.setPassword(invalidPassword);
        userDTO.setFirstName(firstName);
        userDTO.setLastName(lastName);
        userDTO.setRoleId(roleId);

        Set<ConstraintViolation<UserDTO>> constraintViolations = validator.validate(userDTO);
        Assertions.assertEquals(1, constraintViolations.size());
    }

    @Test
    void testBlankPassword() {
        userDTO.setEmail(validEmail);
        userDTO.setFirstName(firstName);
        userDTO.setLastName(lastName);
        userDTO.setRoleId(roleId);

        Set<ConstraintViolation<UserDTO>> constraintViolations = validator.validate(userDTO);
        Assertions.assertEquals(1, constraintViolations.size());
    }

    @Test
    void testNullPassword() {
        userDTO.setEmail(validEmail);
        userDTO.setPassword(null);
        userDTO.setFirstName(firstName);
        userDTO.setLastName(lastName);
        userDTO.setRoleId(roleId);

        Set<ConstraintViolation<UserDTO>> constraintViolations = validator.validate(userDTO);
        Assertions.assertEquals(1, constraintViolations.size());
    }

    @Test
    void testNullFirstName() {
        userDTO.setEmail(validEmail);
        userDTO.setPassword(validPassword);
        userDTO.setFirstName(null);
        userDTO.setLastName(lastName);
        userDTO.setRoleId(roleId);

        Set<ConstraintViolation<UserDTO>> constraintViolations = validator.validate(userDTO);
        Assertions.assertEquals(1, constraintViolations.size());
    }

    @Test
    void testBlankFirstName() {
        userDTO.setEmail(validEmail);
        userDTO.setPassword(validPassword);
        userDTO.setFirstName("");
        userDTO.setLastName(lastName);
        userDTO.setRoleId(roleId);

        Set<ConstraintViolation<UserDTO>> constraintViolations = validator.validate(userDTO);
        Assertions.assertEquals(1, constraintViolations.size());
    }

    @Test
    void testNullLastName() {
        userDTO.setEmail(validEmail);
        userDTO.setPassword(validPassword);
        userDTO.setFirstName(firstName);
        userDTO.setLastName(null);
        userDTO.setRoleId(roleId);

        Set<ConstraintViolation<UserDTO>> constraintViolations = validator.validate(userDTO);
        Assertions.assertEquals(1, constraintViolations.size());
    }

    @Test
    void testBlankLastName() {
        userDTO.setEmail(validEmail);
        userDTO.setPassword(validPassword);
        userDTO.setFirstName(firstName);
        userDTO.setLastName("");
        userDTO.setRoleId(roleId);

        Set<ConstraintViolation<UserDTO>> constraintViolations = validator.validate(userDTO);
        Assertions.assertEquals(1, constraintViolations.size());
    }

    @Test
    void testNegativeRoleId() {
        userDTO.setEmail(validEmail);
        userDTO.setPassword(validPassword);
        userDTO.setFirstName(firstName);
        userDTO.setLastName(lastName);
        userDTO.setRoleId(-1);

        Set<ConstraintViolation<UserDTO>> constraintViolations = validator.validate(userDTO);
        Assertions.assertEquals(1, constraintViolations.size());
    }

    @Test
    void testNullRoleId() {
        userDTO.setEmail(validEmail);
        userDTO.setPassword(validPassword);
        userDTO.setFirstName(firstName);
        userDTO.setLastName(lastName);
        userDTO.setRoleId(null);

        Set<ConstraintViolation<UserDTO>> constraintViolations = validator.validate(userDTO);
        Assertions.assertEquals(1, constraintViolations.size());
    }
}
