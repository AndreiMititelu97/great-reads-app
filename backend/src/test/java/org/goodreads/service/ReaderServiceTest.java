package org.goodreads.service;

import org.greatreads.exception.BookNotFoundException;
import org.greatreads.exception.UserNotFoundException;
import org.greatreads.model.Book;
import org.greatreads.model.User;
import org.greatreads.model.UserToBooks;
import org.greatreads.repository.BookRepository;
import org.greatreads.repository.UserRepository;
import org.greatreads.repository.UserToBooksRepository;
import org.greatreads.service.impl.ReaderServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ReaderServiceTest {
    @Mock
    UserRepository userRepository;

    @Mock
    BookRepository bookRepository;

    @Mock
    UserToBooksRepository userToBooksRepository;

    @InjectMocks
    ReaderServiceImpl readerService;

    @Test
    void testMarkBookAsRead_ExistingRow() {
        User user = new User();
        user.setId(1);
        Book book = new Book();
        book.setId(2);

        UserToBooks userToBooks = new UserToBooks();
        userToBooks.setUser(user);
        userToBooks.setBook(book);

        Mockito.when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        Mockito.when(bookRepository.findById(book.getId())).thenReturn(Optional.of(book));
        Mockito.when(userToBooksRepository.findByUserAndBook(user, book)).thenReturn(Optional.of(userToBooks));

        readerService.markBookAsRead(book.getId(), user.getId());

        Assertions.assertNotNull(userToBooks);
        Assertions.assertTrue(userToBooks.isRead());
        Assertions.assertEquals(book, userToBooks.getBook());
        Assertions.assertEquals(user, userToBooks.getUser());
    }

    @Test
    void testMarkBookAsRead_NewRow() {
        User user = new User();
        user.setId(1);
        Book book = new Book();
        book.setId(2);

        Mockito.when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        Mockito.when(bookRepository.findById(book.getId())).thenReturn(Optional.of(book));
        Mockito.when(userToBooksRepository.findByUserAndBook(user, book)).thenReturn(Optional.empty());

        ArgumentCaptor<UserToBooks> captor = ArgumentCaptor.forClass(UserToBooks.class);
        readerService.markBookAsRead(book.getId(), user.getId());

        verify(userToBooksRepository).save(captor.capture());
        UserToBooks userToBooks = captor.getValue();

        Assertions.assertNotNull(userToBooks);
        Assertions.assertTrue(userToBooks.isRead());
        Assertions.assertEquals(book, userToBooks.getBook());
        Assertions.assertEquals(user, userToBooks.getUser());
    }

    @Test
    void testMarkBookAsRead_UserNotFound() {
        int userId = 1;
        int bookId = 2;
        Mockito.when(userRepository.findById(userId)).thenThrow(new UserNotFoundException(userId));
        assertThrows(UserNotFoundException.class, () -> readerService.markBookAsRead(bookId,userId));
    }

    @Test
    void testMarkBookAsRead_BookNotFound() {
        User user = new User();
        user.setId(1);
        int userId = 1;
        int bookId = 2;
        Mockito.when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        Mockito.when(bookRepository.findById(bookId)).thenThrow(new BookNotFoundException(bookId));
        assertThrows(BookNotFoundException.class, () -> readerService.markBookAsRead(bookId,userId));
    }

    @Test
    void testAddToWishlist_UserNotFound() {
        int userId = 1;
        int bookId = 2;
        Mockito.when(userRepository.findById(userId)).thenThrow(new UserNotFoundException(userId));
        assertThrows(UserNotFoundException.class, () -> readerService.addBookToWishlist(bookId,userId));
    }

    @Test
    void testAddToWishlist_BookNotFound() {
        User user = new User();
        user.setId(1);
        int userId = 1;
        int bookId = 2;
        Mockito.when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        Mockito.when(bookRepository.findById(bookId)).thenThrow(new BookNotFoundException(bookId));
        assertThrows(BookNotFoundException.class, () -> readerService.addBookToWishlist(bookId,userId));
    }

    @Test
    void testAddToWishlist() {
        User user = new User();
        user.setId(1);
        Book book = new Book();
        book.setId(2);

        Mockito.when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        Mockito.when(bookRepository.findById(book.getId())).thenReturn(Optional.of(book));
        Mockito.when(userToBooksRepository.findByUserAndBook(user, book)).thenReturn(Optional.empty());

        ArgumentCaptor<UserToBooks> captor = ArgumentCaptor.forClass(UserToBooks.class);
        readerService.addBookToWishlist(book.getId(), user.getId());

        verify(userToBooksRepository).save(captor.capture());
        UserToBooks userToBooks = captor.getValue();

        Assertions.assertNotNull(userToBooks);
        Assertions.assertTrue(userToBooks.isWishlist());
        Assertions.assertEquals(book, userToBooks.getBook());
        Assertions.assertEquals(user, userToBooks.getUser());
    }
}
