package org.greatreads.controller;

import lombok.RequiredArgsConstructor;
import org.greatreads.service.AdministratorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/administrator")
@RequiredArgsConstructor
public class AdministratorController {
    private final AdministratorService administratorService;

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
}
