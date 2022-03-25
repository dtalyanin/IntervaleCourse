package ru.intervale.course.model.responses;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import ru.intervale.course.model.enums.OperationType;

/**
 * Результат выполнения операции с книгой
 */
@Data
@Schema(description = "Результат выполнения операции с книгой")
public class BookLibraryResult {
    private OperationType operation;
    private String result;

    public BookLibraryResult(OperationType operation, String result) {
        this.operation = operation;
        this.result = result;
    }
}
