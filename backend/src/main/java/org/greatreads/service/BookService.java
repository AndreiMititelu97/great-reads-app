package org.greatreads.service;

import org.greatreads.dto.BookDTO;
import org.greatreads.model.Book;

import java.util.List;

public interface BookService {
    List<Book> getAllBooks();
    List<Book> getBooksByGenre(String genre);
    Book addBook(BookDTO bookDTO);
    Book updateBook(int bookId, BookDTO bookDTO);
    void deleteBook(int bookId);
    void uploadBook(int bookId, String url);
}
