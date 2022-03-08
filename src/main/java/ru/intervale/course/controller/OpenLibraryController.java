package ru.intervale.course.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.intervale.course.service.BookService;

import javax.validation.constraints.NotBlank;
import java.util.Map;

@Validated
@RestController
public class OpenLibraryController {
    @Autowired
    BookService service;

    @GetMapping("/author/{author}")
    public ResponseEntity<Map<String, Object>> getBooksByAuthor(
            @PathVariable @NotBlank(message = "Author name for search cannot be empty") String author) {
        return new ResponseEntity<>(service.getBooksByAuthor(author), HttpStatus.OK);
    }
}
