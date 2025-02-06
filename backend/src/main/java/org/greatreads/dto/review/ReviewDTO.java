package org.greatreads.dto.review;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewDTO {
    @NotNull(message = "Rating can not be null")
    @Min(value = 1, message = "Rating must be at least 1")
    @Max(value = 5, message = "Rating must be at most 5")
    private Integer rating;

    @Size(max = 500, message = "Comment cannot exceed 500 characters")
    private String comment;

    @NotNull(message = "User ID can not be null")
    private Integer userId;

    @NotNull(message = "Book ID can not be null")
    private Integer bookId;
}
