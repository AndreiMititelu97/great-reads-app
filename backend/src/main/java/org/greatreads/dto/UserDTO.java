package org.greatreads.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
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
    @NotNull(message = "First name can not be null")
    private String firstName;

    @NotBlank(message = "Last name is mandatory")
    @NotNull(message = "Last name can not be null")
    private String lastName;

    @NotBlank(message = "Role id is mandatory")
    @NotNull(message = "Role id can not be null")
    private Integer roleId;

    private Boolean isBlocked;
    private String avatar;
}
