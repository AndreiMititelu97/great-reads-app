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
    @NotNull(message = "Title can not be null")
    @NotBlank(message = "Title is mandatory")
    @Size(max = 255, message = "Title cannot exceed 255 characters")
    private String title;

    @Size(max = 2000, message = "Description cannot exceed 2000 characters")
    private String description;

    @NotBlank(message = "Genre ID is mandatory")
    @NotNull(message = "Genre ID can not be null")
    private Integer genreId;

    @NotBlank(message = "ISBN ID is mandatory")
    @NotNull(message = "ISBN can not be null")
    private String isbn;

    @NotBlank(message = "Author ID is mandatory")
    @NotNull(message = "Author ID can not be null")
    private int authorId;

    @NotBlank(message = "Published date ID is mandatory")
    @NotNull(message = "Published date ID can not be null")
    private LocalDateTime publishedDate;

    private String urlLink;
    private Boolean isApproved;
    private String pageCover;
}
