package org.greatreads.repository;

import org.greatreads.model.Book;
import org.greatreads.model.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
    Optional<Book> findByIsbn(String isbn);

    List<Book> findAllByGenre(Genre genre);
}
