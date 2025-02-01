package org.greatreads.exception;

public class ReviewNotFoundException extends RuntimeException {
    public ReviewNotFoundException(int reviewId) {
        super("Review with id: " + reviewId + " was not found");
    }
}
