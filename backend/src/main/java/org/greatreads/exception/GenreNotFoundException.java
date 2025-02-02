package org.greatreads.exception;

public class GenreNotFoundException extends RuntimeException {
    public GenreNotFoundException(String genreName) {
        super("No genre found for: " + genreName);
    }

    public GenreNotFoundException(int genreId) {
        super("No genre found for id: " + genreId);
    }
}
