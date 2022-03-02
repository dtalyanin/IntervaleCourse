package ru.intervale.course.exception;

import lombok.Getter;

@Getter
public class IncorrectBookIdException extends IllegalArgumentException {
    private int id;

    public IncorrectBookIdException(String s, int id) {
        super(s);
        this.id = id;
    }
}
