package org.greatreads.service;

import org.greatreads.dto.book.BookDTO;
import org.greatreads.dto.book.BookResponseDTO;

import java.util.List;

public interface BookService {
    List<BookResponseDTO> getBooks();
    List<BookResponseDTO> getBooksByGenre(String genre);
    BookResponseDTO addBook(BookDTO bookDTO);
    BookResponseDTO updateBook(int bookId, BookDTO bookDTO);
    void deleteBook(int bookId);
    void uploadBook(int bookId, String url);
    List<BookResponseDTO> getReadBooks(int userId);
    List<BookResponseDTO> getWishlistBooks(int userId);
}
