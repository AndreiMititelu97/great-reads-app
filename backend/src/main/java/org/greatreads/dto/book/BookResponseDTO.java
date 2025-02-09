package org.greatreads.dto.book;

import lombok.Getter;
import lombok.Setter;
import org.greatreads.dto.genre.GenreResponseDTO;
import org.greatreads.dto.user.UserSimpleResponseDTO;

import java.time.LocalDate;

@Getter
@Setter
public class BookResponseDTO {
    private int id;
    private String title;
    private String description;
    private GenreResponseDTO genre;
    private String isbn;
    private UserSimpleResponseDTO author;
    private LocalDate publishDate;
    private String urlLink;
    private boolean isApproved;
    private String pageCover;
    private int numberOfReviews;
    private double rating;
}
