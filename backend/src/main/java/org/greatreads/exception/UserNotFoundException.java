package org.greatreads.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(int userId) {
        super("User with id: " + userId + " was not found");
    }

    public UserNotFoundException(String email) {
      super("User with email " + email + " does not exist");
    }
}
