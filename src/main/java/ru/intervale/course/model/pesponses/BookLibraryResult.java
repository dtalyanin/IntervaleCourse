package ru.intervale.course.model.pesponses;

import lombok.Data;

@Data
public class BookLibraryResult {
    private String operation;
    private String result;

    public BookLibraryResult(String operation, String result) {
        this.operation = operation;
        this.result = result;
    }
}
