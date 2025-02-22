package org.greatreads.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.greatreads.dto.book.BookDTO;
import org.greatreads.dto.book.BookResponseDTO;
import org.greatreads.service.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/author")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:8082")
public class AuthorController {
    private final BookService bookService;

    @PostMapping
    public ResponseEntity<BookResponseDTO> addBook(@RequestBody @Valid BookDTO bookDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(bookService.addBook(bookDTO));
    }

    @PostMapping("/{bookId}")
    public ResponseEntity<Void> uploadBook(@PathVariable int bookId, @RequestParam String url) {
        bookService.uploadBook(bookId, url);
        return ResponseEntity.ok().build();
    }
}