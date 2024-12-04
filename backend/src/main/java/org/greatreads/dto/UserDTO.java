package org.greatreads.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class UserDTO {
    @NotNull(message = "email must not be null")
    @Email
    private String email;

    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$",
            message = "password must have digits, punctuation, symbols, lowercase and uppercase characters. " +
                    "Length must be between 8 and 20 characters.")
    private String password;

    @NotNull(message = "First name must not be null")
    @NotBlank(message = "First name must not be blank")
    private String firstName;

    @NotNull(message = "Last name must not be null")
    @NotBlank(message = "Last name must not be blank")
    private String lastName;
}
