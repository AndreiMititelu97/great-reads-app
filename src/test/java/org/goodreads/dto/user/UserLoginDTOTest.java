package org.goodreads.dto.user;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.greatreads.dto.user.UserLoginDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

class UserLoginDTOTest {
    private static final String EMAIL = "test@email.com";
    private static final String PASSWORD = "C]x@{8N3O#4&[%zB$Chy";
    private Validator validator;
    private UserLoginDTO userLoginDTO;

    @BeforeEach
    void setUp() {
        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            validator = factory.getValidator();
        }
        userLoginDTO = new UserLoginDTO();
    }

    @Test
    void testValidUserLoginDTO() {
        userLoginDTO.setEmail(EMAIL);
        userLoginDTO.setPassword(PASSWORD);

        Set<ConstraintViolation<UserLoginDTO>> constraintViolations = validator.validate(userLoginDTO);
        Assertions.assertTrue(constraintViolations.isEmpty());
    }

    @Test
    void testEmail() {
        //Invalid email format
        userLoginDTO.setEmail("invalid");
        userLoginDTO.setPassword(PASSWORD);

        Set<ConstraintViolation<UserLoginDTO>> constraintViolations = validator.validate(userLoginDTO);
        Assertions.assertEquals(1, constraintViolations.size());

        //Not email
        userLoginDTO.setEmail(null);
        userLoginDTO.setPassword(PASSWORD);

        constraintViolations = validator.validate(userLoginDTO);
        Assertions.assertEquals(1, constraintViolations.size());

        //Blank email
        userLoginDTO.setEmail("");
        userLoginDTO.setPassword(PASSWORD);

        constraintViolations = validator.validate(userLoginDTO);
        Assertions.assertEquals(1, constraintViolations.size());
    }

    @Test
    void testPassword() {
        //Blank password
        userLoginDTO.setEmail(EMAIL);
        userLoginDTO.setPassword("");

        Set<ConstraintViolation<UserLoginDTO>> constraintViolations = validator.validate(userLoginDTO);
        Assertions.assertEquals(1, constraintViolations.size());

        //Null password
        userLoginDTO.setEmail(EMAIL);
        userLoginDTO.setPassword(null);

        constraintViolations = validator.validate(userLoginDTO);
        Assertions.assertEquals(1, constraintViolations.size());
    }
}