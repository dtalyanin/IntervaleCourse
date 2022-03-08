package ru.intervale.course.model.responses;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.AllArgsConstructor;
import lombok.Data;
import ru.intervale.course.model.enums.ErrorCode;

/**
 * Класс, содержащий информацию об возникшей ошибке и её причинах
 */
@Data
@AllArgsConstructor
@JsonInclude(Include.NON_NULL)
public class ErrorResponse {
    private ErrorCode error;
    private String parameter;
    private Object invalidValue;
    private String message;

    public ErrorResponse(ErrorCode error, String message) {
        this.error = error;
        this.message = message;
    }
}
