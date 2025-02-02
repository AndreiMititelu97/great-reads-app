package org.greatreads.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.greatreads.dto.BookDTO;
import org.greatreads.exception.BookNotFoundException;
import org.greatreads.exception.GenreNotFoundException;
import org.greatreads.exception.UserNotFoundException;
import org.greatreads.model.Book;
import org.greatreads.model.Genre;
import org.greatreads.model.User;
import org.greatreads.repository.BookRepository;
import org.greatreads.repository.GenreRepository;
import org.greatreads.repository.UserRepository;
import org.greatreads.service.BookService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final GenreRepository genreRepository;
    private final UserRepository userRepository;

    @Override
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    public List<Book> getBooksByGenre(String genreName) {
        Genre genre = genreRepository.findByName(genreName).orElseThrow(() -> new GenreNotFoundException(genreName));
        return bookRepository.findAllByGenre(genre);
    }

    @Override
    @Transactional
    public Book addBook(BookDTO bookDTO) {
        User user = userRepository.findById(bookDTO.getAuthorId())
                .orElseThrow(() -> new UserNotFoundException(bookDTO.getAuthorId()));
        Genre genre = genreRepository.findById(bookDTO.getGenreId())
                .orElseThrow(() -> new GenreNotFoundException(bookDTO.getGenreId()));

        Book book = new Book();
        book.setAuthor(user);
        book.setTitle(bookDTO.getTitle());
        book.setDescription(bookDTO.getDescription());
        book.setGenre(genre);
        book.setIsbn(bookDTO.getIsbn());
        book.setPublishDate(book.getPublishDate());
        book.setUrlLink(bookDTO.getUrlLink());
        book.setApproved(bookDTO.getIsApproved());
        book.setPageCover(bookDTO.getPageCover());

        return bookRepository.save(book);
    }

    @Override
    @Transactional
    public Book updateBook(int bookId, BookDTO bookDTO) {
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new BookNotFoundException(bookId));

        if (bookDTO.getAuthorId() != null) {
            User user = userRepository.findById(bookDTO.getAuthorId())
                    .orElseThrow(() -> new UserNotFoundException(bookDTO.getAuthorId()));
            book.setAuthor(user);
        }
        if (bookDTO.getGenreId() != null) {
            Genre genre = genreRepository.findById(bookDTO.getGenreId())
                    .orElseThrow(() -> new GenreNotFoundException(bookDTO.getGenreId()));
            book.setGenre(genre);
        }

        book.setTitle(bookDTO.getTitle());
        book.setDescription(bookDTO.getDescription());
        book.setIsbn(bookDTO.getIsbn());
        book.setPublishDate(bookDTO.getPublishedDate());
        book.setUrlLink(bookDTO.getUrlLink());
        book.setApproved(bookDTO.getIsApproved());
        book.setPageCover(bookDTO.getPageCover());

        return bookRepository.save(book);
    }

    @Override
    @Transactional
    public void deleteBook(int bookId) {
        if (!bookRepository.existsById(bookId)) {
            throw new BookNotFoundException(bookId);
        }
        bookRepository.deleteById(bookId);
    }

    @Override
    @Transactional
    public void uploadBook(int bookId, String url) {
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new BookNotFoundException(bookId));
        book.setUrlLink(url);
        bookRepository.save(book);
    }
}
