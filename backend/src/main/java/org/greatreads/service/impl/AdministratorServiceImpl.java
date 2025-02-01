package org.greatreads.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.greatreads.exception.BookNotFoundException;
import org.greatreads.model.Book;
import org.greatreads.repository.BookRepository;
import org.greatreads.service.AdministratorService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdministratorServiceImpl implements AdministratorService {
    private final BookRepository bookRepository;

    @Override
    @Transactional
    public void approveBook(int bookId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new BookNotFoundException("Book with id: " + bookId + "was not found"));
        book.setApproved(true);
    }

    @Override
    @Transactional
    public void rejectBook(int bookId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new BookNotFoundException("Book with id: " + bookId + "was not found"));
        book.setApproved(false);
    }
}
