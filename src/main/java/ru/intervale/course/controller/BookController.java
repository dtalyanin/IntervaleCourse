package ru.intervale.course.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.intervale.course.model.Book;
import ru.intervale.course.model.responses.BookLibraryResult;
import ru.intervale.course.service.BookService;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.List;

/**
 * Контроллер для работы с внутренней базой данных.
 */

@Tag(name = "Book Controller", description = "Контроллер для работы с книгами из БД приложения.")
@Validated
@RestController
public class BookController {
    @Autowired
    BookService bookService;
    private static  final String ID_MESSAGE = "ID cannot be less than 1";

    /**
     * Получение всех книг из БД
     * @return Список книг из БД
     */
    @Operation(summary = "Получение всех книг из БД.")
    @GetMapping("/books")
    public List<Book> getBooks() {
        return bookService.getBooks();
    }

    /**
     * "Получение книги из БД по её ID
     * @param id ID книги в БД
     * @return Книга с указанным ID
     */
    @Operation(summary = "Получение книги из БД по её ID.")
    @GetMapping("/book/{id}")
    public Book getBookById(@PathVariable(value = "id") @Min(value = 1, message = ID_MESSAGE) int id) {
        return bookService.getBookById(id);
    }

    /**
     * Изменение книги с указанным ID в БД
     * @param book Книга для изменения
     * @return Результат изменения книги в БД
     */
    @Operation(summary = "Изменение книги с указанным ID в БД.")
    @PostMapping("/edit/{id}")
    public BookLibraryResult editBook(@PathVariable(value = "id") @Min(value = 1, message = ID_MESSAGE) int id,
                                      @Valid @RequestBody Book book) {
        return bookService.editBook(id, book);
    }

    /**
     * Добавление новой книги в БД
     * @param book Книга для добавления в БД
     * @return Результат добавления книги в БД
     */
    @Operation(summary = "Добавление новой книги в БД.")
    @PutMapping("/add")
    public BookLibraryResult addBook(@Valid @RequestBody Book book) {
        return bookService.addBook(book);
    }

    /**
     * Удаление книги из БД по её ID
     * @param id ID книги для удаления
     * @return Результат удаления книги из БД
     */
    @Operation(summary = "Удаление книги из БД по её ID.")
    @DeleteMapping("/delete/{id}")
    public BookLibraryResult deleteBook(@PathVariable(value = "id") @Min(value = 1, message = ID_MESSAGE) int id) {
        return bookService.deleteBookById(id);
    }
}
