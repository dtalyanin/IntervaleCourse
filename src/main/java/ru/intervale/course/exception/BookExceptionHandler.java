package ru.intervale.course.exception;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import javax.validation.ConstraintViolationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import ru.intervale.course.external.open_library.exception.OpenLibraryException;

/**
 * Глобальный обработчик исключений
 */
@ControllerAdvice
public class BookExceptionHandler {
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity findValidationException(ConstraintViolationException e) {
        return new ResponseEntity(e.getMessage(), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity findIncorrectArgument(MethodArgumentNotValidException e) {
        return new ResponseEntity("Incorrect argument to initialization.", HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(NumberFormatException.class)
    public ResponseEntity cameNotNumber(NumberFormatException e) {
        return new ResponseEntity("Incorrect value for number.", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity findInvalidData(NullPointerException e) {
        return new ResponseEntity("Invalid input data.", HttpStatus.BAD_REQUEST);
    }


//    @ExceptionHandler(JsonProcessingException.class)
//    public ResponseEntity cameIncorrectJson(JsonProcessingException e) {
//        return new ResponseEntity("Invalid json.", HttpStatus.BAD_REQUEST);
//    }

    @ExceptionHandler(OpenLibraryException.class)
    public ResponseEntity getBadResponseFromOpenLibrary(OpenLibraryException e) {
        return new ResponseEntity(e.toString(), HttpStatus.BAD_REQUEST);
    }
}
