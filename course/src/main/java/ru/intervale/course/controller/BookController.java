package ru.intervale.course.controller;

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

@Validated
@RestController
public class BookController {
    @Autowired
    private BookServiceImpl service;

    @GetMapping("/books")
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

    @GetMapping("/book")
    public ResponseEntity<Book> getBookById(@RequestParam(value = "id") @Min(value = 1) int id) {
        return new ResponseEntity(service.getBookById(id), HttpStatus.OK);

    }

    @PostMapping("/edit")
    public ResponseEntity<String> editBook(@Validated @RequestBody BookDto book) {
        return new ResponseEntity<>(service.editBook(book), HttpStatus.OK);
    }

    @PutMapping("/add")
    public ResponseEntity<String> addBook(@Validated @RequestBody Book book) {
        return new ResponseEntity<>(service.addBook(book), HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteBook(@RequestParam(value = "id") @Min(value = 1) int id) {
        return new ResponseEntity<>(service.deleteBook(id), HttpStatus.OK);
    }
}
