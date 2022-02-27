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
import org.springframework.web.bind.annotation.*;
import ru.intervale.course.model.Book;
import ru.intervale.course.model.BookDto;
import ru.intervale.course.service.impl.BookServiceImpl;

import javax.validation.constraints.*;
import java.util.List;

/**
 * Контроллер для работы с внутренней базой данных.
 */
@Validated
@RestController
@Tag(name = "Book Controller", description = "Контроллер для работы с книгами из БД приложения.")
public class BookController {
    @Autowired
    private BookServiceImpl service;

    /**
     * Получить все книги из БД
     * @return Список книг из БД
     */
    @GetMapping("/books")
    @Operation(summary = "Получить все книги из БД.")
    @ApiResponse(description = "Список книг из БД.", responseCode = "200", content = @Content(
            array = @ArraySchema(schema = @Schema(implementation = Book.class))))
    public ResponseEntity<Object> getBooks() {
        List<Book> books = service.getBooks();
        ResponseEntity<Object> response;
        if (books.size() != 0) {
            response = new ResponseEntity<>(books, HttpStatus.OK);
        }
        else {
            response = new ResponseEntity<>("No data found in data base.", HttpStatus.OK);
        }
        return response;
    }

    /**
     * "Получить книгу из БД по её ID
     * @param id ID книги в БД
     * @return Книга с указанным ID
     */
    @GetMapping("/book")
    @Operation(summary = "Получить книгу из БД по её ID.")
    @ApiResponse(description = "Книга с указанным ID.", responseCode = "200", content = @Content(
            schema = @Schema(implementation = Book.class)))
    public ResponseEntity getBookById(@RequestParam(value = "id") @Parameter(description = "ID книги в БД.") @Min(value = 1) int id) {
        Book book = service.getBookById(id);
        ResponseEntity response;
        if (book != null) {
            response = new ResponseEntity(book, HttpStatus.OK);
        }
        else {
            response = new ResponseEntity("No book found for ID = " + id, HttpStatus.OK);
        }
        return response;
    }

    /**
     * Изменить книгу в БД согласно переданным полям
     * @param book Книга, содержащая поля для изменения
     * @return Результат изменения книги в БД
     */
    @PostMapping("/edit")
    @Operation(summary = "Изменить книгу в БД согласно переданным полям.")
    @ApiResponse(description = "Результат изменения книги в БД.", responseCode = "200")
    public ResponseEntity<String> editBook(@Validated @RequestBody  @io.swagger.v3.oas.annotations.parameters.RequestBody
            (description = "Книга, содержащая поля для изменения.") BookDto book) {
        return new ResponseEntity<>(service.editBook(book), HttpStatus.OK);
    }

    /**
     * Добавить новую книгу в БД
     * @param book Книга для добавления в БД
     * @return Результат добавления книги в БД
     */
    @PutMapping("/add")
    @Operation(summary = "Добавить новую книгу в БД.")
    @ApiResponse(description = "Результат добавления книги в БД.", responseCode = "200")
    public ResponseEntity<String> addBook(@Validated @RequestBody @io.swagger.v3.oas.annotations.parameters.RequestBody
            (description = "Книга для добавления в БД.") Book book) {
        return new ResponseEntity<>(service.addBook(book), HttpStatus.OK);
    }

    /**
     * Удалить книгу из БД по её ID
     * @param id ID книги для удаления
     * @return Результат удаления книги из БД
     */
    @DeleteMapping("/delete")
    @Operation(summary = "Удалить книгу из БД по её ID.")
    @ApiResponse(description = "Результат удаления книги из БД.", responseCode = "200")
    public ResponseEntity<String> deleteBook(@RequestParam(value = "id") @Parameter(description =
            "ID книги для удаления.")@Min(value = 1) int id) {
        return new ResponseEntity<>(service.deleteBook(id), HttpStatus.OK);
    }
}
