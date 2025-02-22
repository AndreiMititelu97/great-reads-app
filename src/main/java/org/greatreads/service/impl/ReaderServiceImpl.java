package org.greatreads.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.greatreads.exception.BookNotFoundException;
import org.greatreads.exception.UserNotFoundException;
import org.greatreads.model.Book;
import org.greatreads.model.User;
import org.greatreads.model.UserToBooks;
import org.greatreads.repository.BookRepository;
import org.greatreads.repository.UserRepository;
import org.greatreads.repository.UserToBooksRepository;
import org.greatreads.service.ReaderService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReaderServiceImpl implements ReaderService {
    private final UserRepository userRepository;
    private final BookRepository bookRepository;
    private final UserToBooksRepository userToBooksRepository;

    @Override
    @Transactional
    public void markBookAsRead(int bookId, int userId) {
        UserToBooks userToBooks = getOrCreateRow(bookId, userId);
        userToBooks.setRead(true);
        userToBooksRepository.save(userToBooks);
    }

    @Override
    public void addBookToWishlist(int bookId, int userId) {
        UserToBooks userToBooks = getOrCreateRow(bookId, userId);
        userToBooks.setWishlist(true);
        userToBooksRepository.save(userToBooks);
    }

    @Override
    public void removeFromWishlist(int bookId, int userId) {
        UserToBooks userToBooks = getOrCreateRow(bookId, userId);
        userToBooks.setWishlist(false);
        userToBooksRepository.save(userToBooks);
    }

    private UserToBooks getOrCreateRow(int bookId, int userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new BookNotFoundException(bookId));
        Optional<UserToBooks> row = userToBooksRepository.findByUserAndBook(user, book);

        UserToBooks userToBooks;
        if (row.isPresent()) {
            userToBooks = row.get();
        } else {
            userToBooks = new UserToBooks();
            userToBooks.setUser(user);
            userToBooks.setBook(book);
        }
        return userToBooks;
    }
}
