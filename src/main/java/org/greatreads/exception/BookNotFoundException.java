package org.greatreads.exception;

public class BookNotFoundException extends RuntimeException {
    public BookNotFoundException(int bookId) {
        super("Book with id: " + bookId + " was not found");
    }
}
