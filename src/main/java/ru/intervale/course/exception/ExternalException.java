package ru.intervale.course.exception;

/**
 * Ошибка при обращении к внешним API
 */
public class ExternalException extends RuntimeException {
    public ExternalException(String message) {
        super(message);
    }
}
