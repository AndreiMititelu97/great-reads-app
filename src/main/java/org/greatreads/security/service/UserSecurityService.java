package org.greatreads.security.service;

public interface UserSecurityService {
    String authenticate(String email, String password);
}