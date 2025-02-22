package org.greatreads.controller;

import lombok.RequiredArgsConstructor;
import org.greatreads.dto.book.BookResponseDTO;
import org.greatreads.service.BookService;
import org.greatreads.service.ReaderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reader")
@RequiredArgsConstructor
public class ReaderController {
    private final ReaderService readerService;
    private final BookService bookService;

    @PutMapping("/{userId}/books/{bookId}/read")
    public ResponseEntity<Void> markBookAsRead(@PathVariable int bookId, @PathVariable int userId) {
        readerService.markBookAsRead(bookId, userId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{userId}/books/{bookId}/wishlist")
    public ResponseEntity<Void> addBookToWishlist(@PathVariable int bookId, @PathVariable int userId) {
        readerService.addBookToWishlist(bookId, userId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{userId}/read")
    public ResponseEntity<List<BookResponseDTO>> getReadBooks(@PathVariable int userId) {
        return ResponseEntity.ok(bookService.getReadBooks(userId));
    }

    @GetMapping("/{userId}/wishlist")
    public ResponseEntity<List<BookResponseDTO>> getWishlistBooks(@PathVariable int userId) {
        return ResponseEntity.ok(bookService.getWishlistBooks(userId));
    }

    @DeleteMapping("/wishlist/{userId}/{bookId}")
    public ResponseEntity<Void> removeFromWishlist(@PathVariable int bookId, @PathVariable int userId) {
        readerService.removeFromWishlist(bookId, userId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/read/{userId}/{bookId}")
    public ResponseEntity<Void> removeBookAsRead(@PathVariable int bookId, @PathVariable int userId) {
        readerService.removeBookAsRead(bookId, userId);
        return ResponseEntity.noContent().build();
    }
}
