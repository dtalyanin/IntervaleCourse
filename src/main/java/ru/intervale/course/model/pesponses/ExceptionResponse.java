package ru.intervale.course.model.pesponses;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ExceptionResponse {
    private String type;
    private String parameter;
    private Object invalidValue;
    private String message;
}
