package org.greatreads.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.greatreads.dto.book.BookDTO;
import org.greatreads.dto.book.BookResponseDTO;
import org.greatreads.service.AdministratorService;
import org.greatreads.service.impl.BookServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/administrator")
@RequiredArgsConstructor
public class AdministratorController {
    private final AdministratorService administratorService;
    private final BookServiceImpl bookService;

    @PutMapping("/books/{bookId}/approve")
    public ResponseEntity<Void> approveBook(@PathVariable int bookId) {
        administratorService.approveBook(bookId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/books/{bookId}/reject")
    public ResponseEntity<Void> rejectBook(@PathVariable int bookId) {
        administratorService.rejectBook(bookId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/books/{bookId}/delete")
    public ResponseEntity<Void> deleteBook(@PathVariable int bookId) {
        bookService.deleteBook(bookId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/books/{bookId}/update")
    public ResponseEntity<BookResponseDTO> updateBook(@PathVariable int bookId, @RequestBody @Valid BookDTO bookDTO) {
        return ResponseEntity.ok(bookService.updateBook(bookId, bookDTO));
    }

    //TODO blockUser
}
