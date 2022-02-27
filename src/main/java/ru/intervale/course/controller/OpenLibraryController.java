package ru.intervale.course.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.intervale.course.external.open_library.model.OpenLibraryBook;
import ru.intervale.course.external.open_library.model.Work;
import ru.intervale.course.service.impl.BookServiceImpl;

import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Map;

/**
 * Контроллер для работы с книгами из БД и библиотеки Open Library
 */
@RestController
@Validated
@Tag(name = "Open Library Controller", description = "Контроллер для работы с книгами в интеграции с Open Library API.")
public class OpenLibraryController {
    @Autowired
    BookServiceImpl service;

    /**
     * Получить работы заданного автора из БД и библиотеки Open Library
     * @param author Ф.И.О. автора
     * @return Работы указанного автора из БД и библиотеки Open Library
     */
    @GetMapping("/works:{author}")
    @Operation(summary = "Получить работы заданного автора из БД и библиотеки Open Library.")
    @ApiResponse(description = "Работы указанного автора из БД и библиотеки Open Library.", responseCode = "200",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = Work.class))))
    public  ResponseEntity<Object> getWorksByAuthor(@PathVariable  @Parameter(description = "Ф.И.О. автора.") @NotBlank String author) {
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

    /**
     * Книги заданного автора из БД и библиотеки Open Library
     * @param author Ф.И.О. автора
     * @return Книги указанного автора найдены в БД
     */
    @GetMapping("/books:{author}")
    @Operation(summary = "Книги заданного автора из БД и библиотеки Open Library.")
    @ApiResponse(description = "Книги указанного автора найдены в БД.", responseCode = "200", content = @Content(array
            = @ArraySchema(schema = @Schema(implementation = OpenLibraryBook.class))))
    public  ResponseEntity<Map<String, Object>> getBooksByAuthor(@PathVariable @Parameter(description = "Ф.И.О. автора.") @NotBlank String author) {
        return new ResponseEntity<>(service.getBooksByAuthor(author), HttpStatus.OK);
    }
}
