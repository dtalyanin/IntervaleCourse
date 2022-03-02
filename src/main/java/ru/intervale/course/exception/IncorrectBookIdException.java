package ru.intervale.course.exception;

import lombok.Getter;

@Getter
public class IncorrectBookIdException extends IllegalArgumentException {
    public IncorrectBookIdException(String s) {
        super(s);
    }
}
