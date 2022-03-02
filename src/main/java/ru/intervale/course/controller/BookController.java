package ru.intervale.course.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.intervale.course.model.Book;
import ru.intervale.course.model.pesponses.BookLibraryResult;
import ru.intervale.course.service.BookService;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.List;

@Validated
@RestController
public class BookController {
    @Autowired
    BookService bookService;

    @GetMapping("/books")
    public List<Book> getBooks() {
        return bookService.getBooks();
    }

    @GetMapping("/book/{id}")
    public Book getBookById(@PathVariable(value = "id") @Min(value = 1) int id) {
        return bookService.getBookById(id);
    }

    @PostMapping("/edit/{id}")
    public BookLibraryResult editBook(@PathVariable(value = "id") @Min(value = 1) int id, @Valid @RequestBody Book book) {
        return new BookLibraryResult("edit", bookService.editBook(id, book));
    }

    @PutMapping("/add")
    public BookLibraryResult addBook(@Valid @RequestBody Book book) {
        return new BookLibraryResult("add", bookService.addBook(book));
    }

    @DeleteMapping("/delete/{id}")
    public BookLibraryResult deleteBook(@PathVariable(value = "id") @Min(value = 1) int id) {
        return new BookLibraryResult("delete", bookService.deleteBookById(id));
    }
}
