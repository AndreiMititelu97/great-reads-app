package org.greatreads.dto.review;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ReviewResponseDTO {
    private Integer id;
    private Integer userId;
    private Integer bookId;
    private Integer rating;
    private String comment;
    private LocalDateTime publishedDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
