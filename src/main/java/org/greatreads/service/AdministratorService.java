package org.greatreads.service;

public interface AdministratorService {
    void approveBook(int bookId);
    void rejectBook(int bookId);
}
