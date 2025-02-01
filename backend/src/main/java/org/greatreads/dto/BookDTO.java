package org.greatreads.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class BookDTO {
    @NotBlank(message = "Title is mandatory")
    @Size(max = 255, message = "Title cannot exceed 255 characters")
    private String title;

    @Size(max = 2000, message = "Description cannot exceed 2000 characters")
    private String description;

    @NotNull(message = "Genre ID can not be null")
    private Integer genreId;

    @NotBlank(message = "ISBN ID is mandatory")
    private String isbn;

    @NotNull(message = "Author ID can not be null")
    private Integer authorId;

    @NotNull(message = "Published date ID can not be null")
    private LocalDateTime publishedDate;

    private String urlLink;

    private Boolean isApproved = false;

    private String pageCover;
}
