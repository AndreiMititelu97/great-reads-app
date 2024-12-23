package org.greatreads.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewDTO {
    @NotNull(message = "Rating can not be null")
    @NotBlank(message = "Rating is mandatory")
    @Pattern(regexp = "[1-5]", message = "Rating must be a value between 1 and 5")
    private String rating;

    @Size(max = 500, message = "Comment cannot exceed 500 characters")
    private String comment;

    @NotBlank(message = "User ID is mandatory")
    @NotNull(message = "User ID can not be null")
    private int userId;

    @NotBlank(message = "Book ID is mandatory")
    @NotNull(message = "Book ID can not be null")
    private int bookId;
}
