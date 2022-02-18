package ru.intervale.course.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.intervale.course.integration.model.Work;
import ru.intervale.course.service.BookService;

import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Map;

@RestController
@Validated
public class OpenLibraryController {
    @Autowired
    BookService service;

    @GetMapping("/works:{author}")
    public  ResponseEntity<Object> getWorksByAuthor(@PathVariable @NotBlank String author) {
        ResponseEntity<Object> response;
        List<Work> works = service.getWorksByAuthor(author);
        if (works.size() != 0) {
            response = new ResponseEntity<>(works, HttpStatus.OK);
        }
        else {
            response = new ResponseEntity<>("No books found for author '" + author + "'.", HttpStatus.OK);
        }
        return response;
    }

    @GetMapping("/books:{author}")
    public  ResponseEntity<Map<String, Object>> getBooksByAuthor(@PathVariable @NotBlank String author) {
        return new ResponseEntity<>(service.getBooksByAuthor(author), HttpStatus.OK);
    }
}
