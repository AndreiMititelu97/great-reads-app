package org.greatreads.controller;

import lombok.RequiredArgsConstructor;
import org.greatreads.dto.book.BookResponseDTO;
import org.greatreads.service.BookService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:8082")
public class BookController {
    private final BookService bookService;

    @GetMapping("/{bookId}")
    public ResponseEntity<BookResponseDTO> getBook(@PathVariable int bookId) {
        return ResponseEntity.ok(bookService.getBookById(bookId));
    }

    @GetMapping()
    public List<BookResponseDTO> getBooks() {
        return bookService.getBooks();
    }

    @GetMapping("/genre/{genreName}")
    public List<BookResponseDTO> getBooksByGenre(@PathVariable String genreName) {
        return bookService.getBooksByGenre(genreName);
    }
}
