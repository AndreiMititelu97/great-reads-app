package org.greatreads.repository;

import org.greatreads.model.Book;
import org.greatreads.model.User;
import org.greatreads.model.UserToBooks;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserToBooksRepository extends JpaRepository<UserToBooks, Integer> {
    Optional<UserToBooks> findByUserAndBook(User user, Book book);
}
