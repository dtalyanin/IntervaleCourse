package ru.intervale.course.external.open_library.exception;

/**
 * Ошибка при обращении к API Open Library
 */
public class OpenLibraryException extends RuntimeException {
    public OpenLibraryException(String message) {
        super(message);
    }

    @Override
    public String toString() {
        return "Exception in Open Library: " + getMessage();
    }
}
