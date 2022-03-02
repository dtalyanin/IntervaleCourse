package ru.intervale.course.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import ru.intervale.course.exception.IncorrectBookIdException;
import ru.intervale.course.model.pesponses.ExceptionResponse;

@ControllerAdvice
public class BookExceptionHandler {
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ExceptionResponse> findValidationExceptionInParameters(ConstraintViolationException e) {
        ConstraintViolation<?> constraintViolation = e.getConstraintViolations().iterator().next();
        ExceptionResponse errorResponse = new ExceptionResponse("Incorrect request parameter",
                constraintViolation.getPropertyPath().toString(),
                constraintViolation.getInvalidValue(),
                constraintViolation.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponse> findBookException(MethodArgumentNotValidException e) {
        FieldError fieldError = e.getFieldErrors().get(0);
        ExceptionResponse errorResponse = new ExceptionResponse("Validation exception for class field",
                fieldError.getField(),
                fieldError.getRejectedValue(),
                fieldError.getDefaultMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IncorrectBookIdException.class)
    public ResponseEntity<ExceptionResponse> notFoundBookWithId(IncorrectBookIdException e) {
        ExceptionResponse errorResponse = new ExceptionResponse("Book not found", "id", e.getId(), e.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }
}
