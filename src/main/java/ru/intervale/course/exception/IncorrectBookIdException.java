package ru.intervale.course.exception;

import lombok.Getter;

/**
 * Ошибка при попытке получить книгу с несуществующим ID
 */
@Getter
public class IncorrectBookIdException extends IllegalArgumentException {
    public IncorrectBookIdException(String s) {
        super(s);
    }
}
