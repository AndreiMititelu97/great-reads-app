package org.goodreads.service;

import org.greatreads.model.Book;
import org.greatreads.repository.BookRepository;
import org.greatreads.service.impl.AdministratorServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class AdministratorServiceTest {
    @InjectMocks
    private AdministratorServiceImpl administratorService;

    @Mock
    private BookRepository bookRepository;

    @Test
    void testApproveBook() {
        Book book = new Book();
        book.setId(5);

        Mockito.when(bookRepository.findById(book.getId())).thenReturn(Optional.of(book));
        administratorService.approveBook(book.getId());
        Assertions.assertTrue(book.isApproved());
    }

    @Test
    void testRejectBook() {
        Book book = new Book();
        book.setId(5);
        book.setApproved(true);

        Mockito.when(bookRepository.findById(book.getId())).thenReturn(Optional.of(book));
        administratorService.rejectBook(book.getId());
        Assertions.assertFalse(book.isApproved());
    }
}
