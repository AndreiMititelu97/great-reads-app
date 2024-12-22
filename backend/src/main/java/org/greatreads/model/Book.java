package org.greatreads.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String title;

    private String description;

    @ManyToOne
    @JoinColumn(name = "genre_id", nullable = false)
    private Genre genre;

    @Column(name = "ISBN", unique = true, nullable = false)
    private String isbn;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User author;

    @Column(name = "published_date", nullable = false)
    private LocalDateTime publishDate;

    @Column(name = "url_link")
    private String urlLink;

    @Column(nullable = false)
    private boolean isApproved = false;

    @Column(name = "page_cover")
    private String pageCover;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
    private List<Review> reviews;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
    private List<UserToBooks> userToBooks;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
