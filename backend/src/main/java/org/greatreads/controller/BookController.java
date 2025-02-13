package org.greatreads.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.greatreads.dto.book.BookDTO;
import org.greatreads.dto.book.BookResponseDTO;
import org.greatreads.service.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;

    @PostMapping
    public ResponseEntity<BookResponseDTO> addBook(@RequestBody @Valid BookDTO bookDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(bookService.addBook(bookDTO));
    }

    @GetMapping("/{bookId}")
    public ResponseEntity<BookResponseDTO> getBook(@PathVariable int bookId) {
        return ResponseEntity.ok(bookService.getBookById(bookId));
    }

    @GetMapping()
    public List<BookResponseDTO> getBooks() {
        return bookService.getBooks();
    }

    @PatchMapping("/{bookId}")
    public ResponseEntity<BookResponseDTO> updateBook(@PathVariable int bookId, @RequestBody @Valid BookDTO bookDTO) {
        return ResponseEntity.ok(bookService.updateBook(bookId, bookDTO));
    }

    @DeleteMapping("/{bookId}")
    public ResponseEntity<Void> deleteBook(@PathVariable int bookId) {
        bookService.deleteBook(bookId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/genre/{genreName}")
    public List<BookResponseDTO> getBooksByGenre(@PathVariable String genreName) {
        return bookService.getBooksByGenre(genreName);
    }

    @PostMapping("/{bookId}")
    public ResponseEntity<Void> uploadBook(@PathVariable int bookId, @RequestParam String url) {
        bookService.uploadBook(bookId, url);
        return ResponseEntity.ok().build();
    }
}
