package org.greatreads.dto.review;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ReviewResponseDTO {
    private Integer id;
    private Integer userId; //TODO replace userId with UserDTO?
    private Integer bookId; //TODO replace bookId with BookDTO?
    private Integer rating;
    private String comment;
    private LocalDateTime publishedDate;
}
