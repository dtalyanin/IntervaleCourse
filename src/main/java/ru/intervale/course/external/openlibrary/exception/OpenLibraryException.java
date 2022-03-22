package ru.intervale.course.external.openlibrary.exception;

/**
 * Ошибка при обращении к API Open Library
 */
public class OpenLibraryException extends RuntimeException {
    public OpenLibraryException(String message) {
        super(message);
    }
}
