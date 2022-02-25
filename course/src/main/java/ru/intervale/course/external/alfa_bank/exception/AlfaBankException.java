package ru.intervale.course.external.alfa_bank.exception;

/**
 * Ошибка при обращении к API Альфа-банка
 */
public class AlfaBankException extends RuntimeException {
    public AlfaBankException(String message) {
        super(message);
    }

    @Override
    public String toString() {
        return "Exception in Alfa-Bank public API: " + getMessage();
    }
}
