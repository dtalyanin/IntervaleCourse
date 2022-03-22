package ru.intervale.course.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.intervale.course.external.openlibrary.service.OpenLibraryService;
import ru.intervale.course.model.BookDTO;
import ru.intervale.course.service.BookService;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * Контроллер для работы с книгами из БД и библиотеки Open Library
 */

@Tag(name = "Open Library Controller", description = "Контроллер для работы с книгами в интеграции с Open Library API.")
@Validated
@RestController
public class OpenLibraryController {
    @Autowired
    BookService service;

    @Autowired
    OpenLibraryService olservice;
    /**
     * Получение книг заданного автора из БД и библиотеки Open Library
     * @param author Ф.И.О. автора
     * @return список книг указанного автора
     */
    @Operation(summary = "Получение книг заданного автора из БД и библиотеки Open Library.")
    @GetMapping("/author/{author}")
    public ResponseEntity<List<BookDTO>> getBooksByAuthor(
            @PathVariable @NotBlank(message = "Author name for search cannot be empty") String author) {
        return new ResponseEntity<>(service.getBooksByAuthor(author), HttpStatus.OK);
    }
}
