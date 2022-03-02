package ru.intervale.course.model.responses;

import lombok.Data;
import ru.intervale.course.model.enums.OperationType;

@Data
public class BookLibraryResult {
    private OperationType operation;
    private String result;

    public BookLibraryResult(OperationType operation, String result) {
        this.operation = operation;
        this.result = result;
    }
}
