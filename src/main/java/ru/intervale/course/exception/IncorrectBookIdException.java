package ru.intervale.course.exception;

public class IncorrectBookIdException extends IllegalArgumentException {
    public IncorrectBookIdException(String s) {
        super(s);
    }
}
