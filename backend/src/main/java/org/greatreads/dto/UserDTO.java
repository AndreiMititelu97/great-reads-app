package org.greatreads.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {
    @NotBlank(message = "Email is mandatory")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Password is mandatory")
    @Pattern(regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!]).{8,20}$",
            message = "Password must be 8-20 characters long and include at least one digit, one lowercase letter, " +
                    "one uppercase letter, and one special character.")
    private String password;

    @NotBlank(message = "First name is mandatory")
    private String firstName;

    @NotBlank(message = "Last name is mandatory")
    private String lastName;

    @Positive(message = "Role id must be positive")
    @NotNull(message = "Role id can not be null")
    private Integer roleId;

    private Boolean isBlocked = false;

    private String avatar;
}
