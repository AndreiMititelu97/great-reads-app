package org.goodreads.dto.user;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.greatreads.dto.user.UserRegisterDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

class UserRegisterDTOTest {
    private static final String EMAIL = "test@email.com";
    private static final String PASSWORD = "C]x@{8N3O#4&[%zB$Chy";
    private static final String FIRST_NAME = "first name";
    private static final String LAST_NAME = "last name";
    private Validator validator;
    private UserRegisterDTO userRegisterDTO;

    @BeforeEach
    void setUp() {
        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            validator = factory.getValidator();
        }
        userRegisterDTO = new UserRegisterDTO();
    }

    @Test
    void testValidUserRegisterDTO() {
        userRegisterDTO.setEmail(EMAIL);
        userRegisterDTO.setPassword(PASSWORD);
        userRegisterDTO.setFirstName(FIRST_NAME);
        userRegisterDTO.setLastName(LAST_NAME);

        Set<ConstraintViolation<UserRegisterDTO>> constraintViolations = validator.validate(userRegisterDTO);
        Assertions.assertTrue(constraintViolations.isEmpty());
    }

    @Test
    void testEmail() {
        //Invalid email format
        userRegisterDTO.setEmail("invalid");
        userRegisterDTO.setPassword(PASSWORD);
        userRegisterDTO.setFirstName(FIRST_NAME);
        userRegisterDTO.setLastName(LAST_NAME);

        Set<ConstraintViolation<UserRegisterDTO>> constraintViolations = validator.validate(userRegisterDTO);
        Assertions.assertEquals(1, constraintViolations.size());

        //Not email
        userRegisterDTO.setEmail(null);
        userRegisterDTO.setPassword(PASSWORD);
        userRegisterDTO.setFirstName(FIRST_NAME);
        userRegisterDTO.setLastName(LAST_NAME);

        constraintViolations = validator.validate(userRegisterDTO);
        Assertions.assertEquals(1, constraintViolations.size());

        //Blank email
        userRegisterDTO.setEmail("");
        userRegisterDTO.setPassword(PASSWORD);
        userRegisterDTO.setFirstName(FIRST_NAME);
        userRegisterDTO.setLastName(LAST_NAME);

        constraintViolations = validator.validate(userRegisterDTO);
        Assertions.assertEquals(1, constraintViolations.size());
    }

    @Test
    void testPassword() {
        //Invalid password
        userRegisterDTO.setEmail(EMAIL);
        userRegisterDTO.setPassword("invalid");
        userRegisterDTO.setFirstName(FIRST_NAME);
        userRegisterDTO.setLastName(LAST_NAME);

        Set<ConstraintViolation<UserRegisterDTO>> constraintViolations = validator.validate(userRegisterDTO);
        Assertions.assertEquals(1, constraintViolations.size());

        //Blank password
        userRegisterDTO.setEmail(EMAIL);
        userRegisterDTO.setPassword("");
        userRegisterDTO.setFirstName(FIRST_NAME);
        userRegisterDTO.setLastName(LAST_NAME);

        constraintViolations = validator.validate(userRegisterDTO);
        Assertions.assertEquals(2, constraintViolations.size());

        //Null password
        userRegisterDTO.setEmail(EMAIL);
        userRegisterDTO.setPassword(null);
        userRegisterDTO.setFirstName(FIRST_NAME);
        userRegisterDTO.setLastName(LAST_NAME);

        constraintViolations = validator.validate(userRegisterDTO);
        Assertions.assertEquals(1, constraintViolations.size());
    }

    @Test
    void testFirstName() {
        //Blank FirstName
        userRegisterDTO.setEmail(EMAIL);
        userRegisterDTO.setPassword(PASSWORD);
        userRegisterDTO.setFirstName("");
        userRegisterDTO.setLastName(LAST_NAME);

        Set<ConstraintViolation<UserRegisterDTO>> constraintViolations = validator.validate(userRegisterDTO);
        Assertions.assertEquals(1, constraintViolations.size());

        //Null FirstName
        userRegisterDTO.setEmail(EMAIL);
        userRegisterDTO.setPassword(PASSWORD);
        userRegisterDTO.setFirstName(null);
        userRegisterDTO.setLastName(LAST_NAME);

        constraintViolations = validator.validate(userRegisterDTO);
        Assertions.assertEquals(1, constraintViolations.size());
    }

    @Test
    void testLastName() {
        //Blank LastName
        userRegisterDTO.setEmail(EMAIL);
        userRegisterDTO.setPassword(PASSWORD);
        userRegisterDTO.setFirstName(FIRST_NAME);
        userRegisterDTO.setLastName("");

        Set<ConstraintViolation<UserRegisterDTO>> constraintViolations = validator.validate(userRegisterDTO);
        Assertions.assertEquals(1, constraintViolations.size());

        //Null LastName
        userRegisterDTO.setEmail(EMAIL);
        userRegisterDTO.setPassword(PASSWORD);
        userRegisterDTO.setFirstName(FIRST_NAME);
        userRegisterDTO.setLastName(null);

        constraintViolations = validator.validate(userRegisterDTO);
        Assertions.assertEquals(1, constraintViolations.size());
    }
}
