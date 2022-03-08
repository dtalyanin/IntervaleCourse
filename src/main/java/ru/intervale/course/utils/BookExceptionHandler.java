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
import ru.intervale.course.external.open_library.exception.OpenLibraryException;
import ru.intervale.course.model.enums.ErrorCode;
import ru.intervale.course.model.responses.ErrorResponse;

/**
 * Обработчик исключений
 */
@ControllerAdvice
public class BookExceptionHandler {
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> findValidationExceptionInParameters(ConstraintViolationException e) {
        ConstraintViolation<?> constraintViolation = e.getConstraintViolations().iterator().next();
        ErrorResponse errorResponse = new ErrorResponse(ErrorCode.VALIDATION_ERROR,
                constraintViolation.getPropertyPath().toString(),
                constraintViolation.getInvalidValue(),
                constraintViolation.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> findBookException(MethodArgumentNotValidException e) {
        FieldError fieldError = e.getFieldErrors().get(0);
        ErrorResponse errorResponse = new ErrorResponse(ErrorCode.VALIDATION_ERROR,
                fieldError.getField(),
                fieldError.getRejectedValue(),
                fieldError.getDefaultMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IncorrectBookIdException.class)
    public ResponseEntity<ErrorResponse> notFoundBookWithId(IncorrectBookIdException e) {
        ErrorResponse errorResponse = new ErrorResponse(ErrorCode.BOOK_NOT_FOUND, e.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(OpenLibraryException.class)
    public ResponseEntity<ErrorResponse> getBadResponseFromOpenLibrary(OpenLibraryException e) {
        ErrorResponse errorResponse = new ErrorResponse(ErrorCode.OPEN_LIBRARY_ERROR, e.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
