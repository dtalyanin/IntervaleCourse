package ru.intervale.course.integration.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.intervale.course.integration.service.OpenLibraryService;
import ru.intervale.course.integration.model.Work;

import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Map;

@RestController
@Validated
public class OpenLibraryController {
    @Autowired
    OpenLibraryService service;

    @GetMapping("/works:{author}")
    public  ResponseEntity getWorksByAuthor(@PathVariable @NotBlank String author) {
        ResponseEntity response;
        List<Work> works = service.getWorksByAuthor(author);
        if (works.size() != 0) {
            response = new ResponseEntity(works, HttpStatus.OK);
        }
        else {
            response = new ResponseEntity("No books found for author \'" + author + "\'.", HttpStatus.BAD_REQUEST);
        }
        return response;
    }

    @GetMapping("/books:{author}")
    public  ResponseEntity getBooksByAuthor(@PathVariable @NotBlank String author) {
        Map<String, Object> books = service.getBooksByAuthor(author);
        return new ResponseEntity(books, HttpStatus.OK);
    }
}
