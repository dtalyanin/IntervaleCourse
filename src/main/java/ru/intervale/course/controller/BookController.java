package ru.intervale.course.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.intervale.course.model.Book;
import ru.intervale.course.model.BookDTO;
import ru.intervale.course.model.responses.BookLibraryResult;
import ru.intervale.course.service.BookService;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.List;

/**
 * Контроллер для выполнения операций с книгами из БД и библиотеки Open Library.
 */

@Tag(name = "Book Controller", description = "Контроллер для выполнения операций с книгами из БД и библиотеки Open Library.")
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

    /**
     * Получение книг заданного автора из всех источников
     * @param author Ф.И.О. автора
     * @return список книг указанного автора
     */
    @Operation(summary = "Получение книг заданного автора из БД и библиотеки Open Library.")
    @GetMapping("/author/{author}")
    public List<BookDTO> getBooksByAuthor(
            @PathVariable @NotBlank(message = "Author name for search cannot be empty") String author) {
        return bookService.getBooksByAuthor(author);
    }

    /**
     * Получение  актуальной стоимости книги по курсу Альфа-банка по её названию
     * @param title Название книги в БД.
     * @return Список книг с заданным названием из БД и стоимостью в различных валютах по курсу Альфа-банка
     */
    @GetMapping("/price/{title}")
    @Operation(summary = "Получение актуальной стоимости книги по курсу Альфа-банка в различных валютах по её названию.")
    public List<BookDTO> getPriceByTitle(
            @PathVariable @NotBlank(message = "Title for search cannot be empty") String title) {
        return bookService.getBooksByNameWithCurrentPrice(title);
    }

    /**
     * Получить стоимость книги в заданном диапазоне по дням в выбранной валюте по курсу НБ РБ (выходные дни не отображаются)
     * @param title название книги для поиска
     * @param currency код валюты согласно ISO
     * @return список книг с заданным названием и диапазоном их стоимости по дням в выбранной валюте
     */
    @GetMapping("/price/{title}/{currency}")
    @Operation(summary = "Получить стоимость книги в заданном диапазоне по дням в выбранной валюте по курсу НБ РБ.")
    public List<BookDTO> getPriceByTitleInRange(
            @PathVariable @NotBlank(message = "Title for search cannot be empty") String title,
            @PathVariable @NotBlank(message = "Currency for search cannot be empty")
            @Pattern(regexp = "\\p{IsAlphabetic}{3}", message = "Incorrect format. Use ISO format for currency (for example USD)") String currency,
            @RequestParam @Min(value = 2, message = "Minimum period is 2 days")
            @Max(value = 30, message = "Maximum period is 30 days") int period) {
        return bookService.getBookByNameWithCurrencyPriceInRange(title, currency, period);
    }
}
