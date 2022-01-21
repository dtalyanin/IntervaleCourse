package ru.intervale.course.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import javax.validation.ConstraintViolationException;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.sql.SQLException;

@ControllerAdvice
public class ValidationException {
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity findValidationException(ConstraintViolationException e) {
        return new ResponseEntity(e.getMessage(), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity findBookException(MethodArgumentNotValidException e) {
        return new ResponseEntity("Incorrect argument to initialization.", HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity findBookExc(NullPointerException e) {
        return new ResponseEntity("Invalid input data.", HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(JsonProcessingException.class)
    public ResponseEntity findBookExc22(JsonProcessingException e) {
        return new ResponseEntity("Invalid json.", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(SQLException.class)
    public ResponseEntity sqlException(SQLException e) {
        return new ResponseEntity("Invalid sql.", HttpStatus.BAD_REQUEST);
    }
}
