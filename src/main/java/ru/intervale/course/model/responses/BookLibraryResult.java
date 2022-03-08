package ru.intervale.course.model.responses;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import ru.intervale.course.model.enums.OperationType;

/**
 * Класс, содержащий сведения о результатах выполнения операции при обращении к БД
 */
@Data
@Schema(description = "Класс, содержащий сведения о результатах выполнения операции при обращении к БД")
public class BookLibraryResult {
    private OperationType operation;
    private String result;

    public BookLibraryResult(OperationType operation, String result) {
        this.operation = operation;
        this.result = result;
    }
}
