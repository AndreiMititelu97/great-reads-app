package org.goodreads.service;

import org.greatreads.dto.book.BookDTO;
import org.greatreads.dto.book.BookResponseDTO;
import org.greatreads.exception.BookNotFoundException;
import org.greatreads.exception.GenreNotFoundException;
import org.greatreads.exception.UserNotFoundException;
import org.greatreads.model.Book;
import org.greatreads.model.Genre;
import org.greatreads.model.User;
import org.greatreads.model.UserToBooks;
import org.greatreads.repository.*;
import org.greatreads.service.impl.BookServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {
    @Mock
    private BookRepository bookRepository;

    @Mock
    private GenreRepository genreRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private UserToBooksRepository userToBooksRepository;

    @InjectMocks
    private BookServiceImpl bookService;

    @Test
    void testGetBooks() {
        Genre genre = new Genre();
        genre.setId(1);
        User user = new User();
        user.setId(1);

        Book book1 = new Book();
        book1.setGenre(genre);
        book1.setAuthor(user);

        Book book2 = new Book();
        book2.setGenre(genre);
        book2.setAuthor(user);

        List<Book> books = List.of(book1, book2);
        Mockito.when(bookRepository.findAll()).thenReturn(books);
        Mockito.when(reviewRepository.countByBook_Id(book1.getId())).thenReturn(1);
        Mockito.when(reviewRepository.findAverageByBook_Id(book1.getId())).thenReturn(1.0);

        List<BookResponseDTO> result = bookService.getBooks();
        Assertions.assertEquals(books.size(), result.size());
    }

    @Test
    void testGetBooks_NoResults() {
        Mockito.when(bookRepository.findAll()).thenReturn(List.of());
        List<BookResponseDTO> result = bookService.getBooks();

        Assertions.assertNotNull(result);
        Assertions.assertEquals(0, result.size());
    }

    @Test
    void testBooksByGenre() {
        User user = new User();
        user.setId(1);

        Genre genre = new Genre();
        genre.setId(1);
        genre.setName("Mystery");

        Book book1 = new Book();
        book1.setGenre(genre);
        book1.setAuthor(user);

        Book book2 = new Book();
        book2.setGenre(genre);
        book2.setAuthor(user);

        List<Book> books = List.of(book1, book2);

        Mockito.when(genreRepository.findByName(genre.getName())).thenReturn(Optional.of(genre));
        Mockito.when(bookRepository.findAllByGenre(genre)).thenReturn(books);

        List<BookResponseDTO> result = bookService.getBooksByGenre(genre.getName());
        Assertions.assertEquals(books.size(), result.size());
    }

    @Test
    void testBooksByGenre_NoResults() {
        Genre genre = new Genre();
        genre.setId(1);
        genre.setName("Mystery");

        Mockito.when(genreRepository.findByName(genre.getName())).thenReturn(Optional.of(genre));
        Mockito.when(bookRepository.findAllByGenre(genre)).thenReturn(List.of());

        List<BookResponseDTO> result = bookService.getBooksByGenre(genre.getName());

        Assertions.assertNotNull(result);
        Assertions.assertEquals(0, result.size());
    }

    @Test
    void testGetBooksByGenre_GenreNotFound() {
        String genreName = "invalid";
        Mockito.when(genreRepository.findByName(genreName)).thenThrow(new GenreNotFoundException(genreName));
        assertThrows(GenreNotFoundException.class, () -> bookService.getBooksByGenre(genreName));
    }

    @Test
    void testAddBook() {
        User user = new User();
        user.setId(1);
        Genre genre = new Genre();
        genre.setId(2);

        BookDTO bookDTO = new BookDTO();
        bookDTO.setAuthorId(user.getId());
        bookDTO.setGenreId(genre.getId());
        bookDTO.setTitle("Title");
        bookDTO.setDescription("Description");
        bookDTO.setIsbn("ISBN");
        bookDTO.setUrlLink("URL");
        bookDTO.setPageCover("Page cover");
        bookDTO.setPublishDate(LocalDate.now());

        Mockito.when(userRepository.findById(bookDTO.getAuthorId())).thenReturn(Optional.of(user));
        Mockito.when(genreRepository.findById(bookDTO.getGenreId())).thenReturn(Optional.of(genre));
        Mockito.when(bookRepository.save(Mockito.any(Book.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        BookResponseDTO result = bookService.addBook(bookDTO);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(bookDTO.getTitle(), result.getTitle());
        Assertions.assertEquals(bookDTO.getDescription(), result.getDescription());
        Assertions.assertEquals(bookDTO.getIsbn(), result.getIsbn());
        Assertions.assertEquals(bookDTO.getUrlLink(), result.getUrlLink());
        Assertions.assertEquals(bookDTO.getPageCover(), result.getPageCover());
        Assertions.assertEquals(bookDTO.getPublishDate(), result.getPublishDate());
        Assertions.assertEquals(user.getId(), result.getAuthor().getId());
        Assertions.assertEquals(genre.getId(), result.getGenre().getId());
    }

    @Test
    void testAddBook_UserNotFound() {
        User user = new User();
        user.setId(1);
        BookDTO bookDTO = new BookDTO();

        Mockito.when(userRepository.findById(bookDTO.getAuthorId())).thenThrow(new UserNotFoundException(user.getId()));

        assertThrows(UserNotFoundException.class, () -> bookService.addBook(bookDTO));
    }

    @Test
    void testAddBook_GenreNotFound() {
        User user = new User();
        user.setId(1);
        Genre genre = new Genre();
        genre.setId(2);
        BookDTO bookDTO = new BookDTO();

        Mockito.when(userRepository.findById(bookDTO.getAuthorId())).thenReturn(Optional.of(user));
        Mockito.when(genreRepository.findById(bookDTO.getGenreId()))
                .thenThrow(new GenreNotFoundException(genre.getId()));

        assertThrows(GenreNotFoundException.class, () -> bookService.addBook(bookDTO));
    }

    @Test
    void testUpdateBook() {
        Book book = new Book();
        book.setId(1);

        User user = new User();
        user.setId(1);
        Genre genre = new Genre();
        genre.setId(2);

        BookDTO bookDTO = new BookDTO();
        bookDTO.setAuthorId(user.getId());
        bookDTO.setGenreId(genre.getId());
        bookDTO.setTitle("Title");
        bookDTO.setDescription("Description");
        bookDTO.setIsbn("ISBN");
        bookDTO.setUrlLink("URL");
        bookDTO.setPageCover("Page cover");
        bookDTO.setPublishDate(LocalDate.now());

        Mockito.when(bookRepository.findById(book.getId())).thenReturn(Optional.of(book));
        Mockito.when(userRepository.findById(bookDTO.getAuthorId())).thenReturn(Optional.of(user));
        Mockito.when(genreRepository.findById(bookDTO.getGenreId())).thenReturn(Optional.of(genre));
        Mockito.when(bookRepository.save(Mockito.any(Book.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        BookResponseDTO result = bookService.updateBook(book.getId(), bookDTO);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(bookDTO.getTitle(), result.getTitle());
        Assertions.assertEquals(bookDTO.getDescription(), result.getDescription());
        Assertions.assertEquals(bookDTO.getIsbn(), result.getIsbn());
        Assertions.assertEquals(bookDTO.getUrlLink(), result.getUrlLink());
        Assertions.assertEquals(bookDTO.getPageCover(), result.getPageCover());
        Assertions.assertEquals(bookDTO.getPublishDate(), result.getPublishDate());
        Assertions.assertEquals(user.getId(), result.getAuthor().getId());
        Assertions.assertEquals(genre.getId(), result.getGenre().getId());
    }

    @Test
    void testUpdateBook_EmptyBookDTO() {
        User user = new User();
        user.setId(1);
        Genre genre = new Genre();
        genre.setId(2);

        Book book = new Book();
        book.setId(1);
        book.setAuthor(user);
        book.setGenre(genre);
        book.setTitle("Title");
        book.setDescription("Description");
        book.setIsbn("ISBN");
        book.setUrlLink("URL");
        book.setPageCover("Page cover");
        book.setPublishDate(LocalDate.now());

        BookDTO bookDTO = new BookDTO();

        Mockito.when(bookRepository.findById(book.getId())).thenReturn(Optional.of(book));
        Mockito.when(bookRepository.save(Mockito.any(Book.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        BookResponseDTO result = bookService.updateBook(book.getId(), bookDTO);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(book.getTitle(), result.getTitle());
        Assertions.assertEquals(book.getDescription(), result.getDescription());
        Assertions.assertEquals(book.getIsbn(), result.getIsbn());
        Assertions.assertEquals(book.getUrlLink(), result.getUrlLink());
        Assertions.assertEquals(book.getPageCover(), result.getPageCover());
        Assertions.assertEquals(book.getPublishDate(), result.getPublishDate());
        Assertions.assertEquals(user.getId(), result.getAuthor().getId());
        Assertions.assertEquals(genre.getId(), result.getGenre().getId());
    }

    @Test
    void testUpdateBook_BookNotFound() {
        int bookId = 1;
        BookDTO bookDTO = new BookDTO();

        Mockito.when(bookRepository.findById(bookId)).thenThrow(new BookNotFoundException(bookId));
        assertThrows(BookNotFoundException.class, () -> bookService.updateBook(bookId, bookDTO));
    }

    @Test
    void testUpdateBook_UserNotFound() {
        Book book = new Book();
        int bookId = 1;
        User user = new User();
        user.setId(1);
        BookDTO bookDTO = new BookDTO();
        bookDTO.setAuthorId(user.getId());


        Mockito.when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
        Mockito.when(userRepository.findById(bookDTO.getAuthorId()))
                .thenThrow(new UserNotFoundException(bookDTO.getAuthorId()));

        assertThrows(UserNotFoundException.class, () -> bookService.updateBook(bookId, bookDTO));
    }

    @Test
    void testUpdateBook_GenreNotFound() {
        Book book = new Book();
        int bookId = 1;
        User user = new User();
        user.setId(1);
        Genre genre = new Genre();
        genre.setId(2);
        BookDTO bookDTO = new BookDTO();
        bookDTO.setAuthorId(user.getId());
        bookDTO.setGenreId(genre.getId());

        Mockito.when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
        Mockito.when(userRepository.findById(bookDTO.getAuthorId())).thenReturn(Optional.of(user));
        Mockito.when(genreRepository.findById(bookDTO.getGenreId()))
                .thenThrow(new GenreNotFoundException(bookDTO.getGenreId()));

        assertThrows(GenreNotFoundException.class, () -> bookService.updateBook(bookId, bookDTO));
    }

    @Test
    void testDeleteBook() {
        int bookId = 1;
        Mockito.when(bookRepository.existsById(bookId)).thenReturn(true);

        bookService.deleteBook(bookId);
        Mockito.verify(bookRepository, Mockito.times(1)).deleteById(bookId);
    }

    @Test
    void testDeleteBook_BookNotFound() {
        int bookId = 1;
        Mockito.when(bookRepository.existsById(bookId)).thenReturn(false);
        Assertions.assertThrows(BookNotFoundException.class, () -> bookService.deleteBook(bookId));
    }

    @Test
    void testUploadBook() {
        String url = "URL";
        Book book = new Book();
        book.setId(1);

        Mockito.when(bookRepository.findById(book.getId())).thenReturn(Optional.of(book));
        bookService.uploadBook(book.getId(), url);

        Assertions.assertEquals(url, book.getUrlLink());
    }

    @Test
    void testUploadBook_BookNotFound() {
        int bookId = 1;
        String url = "URL";
        Mockito.when(bookRepository.findById(bookId)).thenThrow(new BookNotFoundException(bookId));
        Assertions.assertThrows(BookNotFoundException.class, () -> bookService.uploadBook(bookId, url));
    }

    @Test
    void testGetReadBooks() {
        User user = new User();
        user.setId(3);

        Genre genre = new Genre();
        genre.setId(4);

        Book book = new Book();
        book.setGenre(genre);
        book.setAuthor(user);

        Book book2 = new Book();
        book2.setGenre(genre);
        book2.setAuthor(user);

        UserToBooks userToBooks = new UserToBooks();
        userToBooks.setUser(user);
        userToBooks.setBook(book);

        UserToBooks userToBooks2 = new UserToBooks();
        userToBooks2.setUser(user);
        userToBooks2.setBook(book2);

        Mockito.when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        Mockito.when(userToBooksRepository.findByUserAndIsRead(user, true))
                .thenReturn(List.of(userToBooks, userToBooks2));

        List<BookResponseDTO> results = bookService.getReadBooks(user.getId());

        Assertions.assertNotNull(results);
        Assertions.assertEquals(2, results.size());
    }

    @Test
    void testGetReadBooks_noResults() {
        User user = new User();
        user.setId(3);

        Mockito.when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        Mockito.when(userToBooksRepository.findByUserAndIsRead(user, true))
                .thenReturn(List.of());

        List<BookResponseDTO> results = bookService.getReadBooks(user.getId());

        Assertions.assertNotNull(results);
        Assertions.assertEquals(0, results.size());
    }

    @Test
    void testGetReadBooks_UserNotFound() {
        int userId = 1;
        Mockito.when(userRepository.findById(userId)).thenThrow(new UserNotFoundException(userId));
        Assertions.assertThrows(UserNotFoundException.class, () -> bookService.getReadBooks(userId));
    }

    @Test
    void testGetWishlistBooks() {
        User user = new User();
        user.setId(3);

        Genre genre = new Genre();
        genre.setId(4);

        Book book = new Book();
        book.setGenre(genre);
        book.setAuthor(user);

        Book book2 = new Book();
        book2.setGenre(genre);
        book2.setAuthor(user);

        UserToBooks userToBooks = new UserToBooks();
        userToBooks.setUser(user);
        userToBooks.setBook(book);

        UserToBooks userToBooks2 = new UserToBooks();
        userToBooks2.setUser(user);
        userToBooks2.setBook(book2);

        Mockito.when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        Mockito.when(userToBooksRepository.findByUserAndIsWishlist(user, true))
                .thenReturn(List.of(userToBooks, userToBooks2));

        List<BookResponseDTO> results = bookService.getWishlistBooks(user.getId());

        Assertions.assertNotNull(results);
        Assertions.assertEquals(2, results.size());
    }

    @Test
    void testGetWishlistBooks_noResults() {
        User user = new User();
        user.setId(3);

        Mockito.when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        Mockito.when(userToBooksRepository.findByUserAndIsWishlist(user, true))
                .thenReturn(List.of());

        List<BookResponseDTO> results = bookService.getWishlistBooks(user.getId());

        Assertions.assertNotNull(results);
        Assertions.assertEquals(0, results.size());
    }

    @Test
    void testGetWishlistBooks_UserNotFound() {
        int userId = 1;
        Mockito.when(userRepository.findById(userId)).thenThrow(new UserNotFoundException(userId));
        Assertions.assertThrows(UserNotFoundException.class, () -> bookService.getWishlistBooks(userId));
    }

    @Test
    void testGetBookById_BookNotFound() {
        int bookId = 1;
        Mockito.when(bookRepository.findById(bookId)).thenThrow(new BookNotFoundException(bookId));
        Assertions.assertThrows(BookNotFoundException.class, () -> bookService.getBookById(bookId));
    }

    @Test
    void testGetBookById() {
        User user = new User();
        user.setId(3);

        Genre genre = new Genre();
        genre.setId(4);

        Book book = new Book();
        book.setGenre(genre);
        book.setAuthor(user);
        book.setId(1);

        Mockito.when(bookRepository.findById(book.getId())).thenReturn(Optional.of(book));

        BookResponseDTO bookResponseDTO = bookService.getBookById(book.getId());

        Assertions.assertNotNull(bookResponseDTO);
        Assertions.assertEquals(book.getId(), bookResponseDTO.getId());
        Assertions.assertEquals(book.getAuthor().getId(), bookResponseDTO.getAuthor().getId());
        Assertions.assertEquals(book.getGenre().getId(), bookResponseDTO.getGenre().getId());
    }
}
