package org.greatreads.service;

public interface ReaderService {
    void markBookAsRead(int bookId, int userId);
    void addBookToWishlist(int bookId, int userId);
    void removeFromWishlist(int bookId, int userId);
}
