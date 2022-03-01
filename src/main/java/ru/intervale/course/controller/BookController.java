package ru.intervale.course.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.intervale.course.model.Book;
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
    public ResponseEntity<Book> getBookById(@PathVariable(value = "id") @Min(value = 1) int id) {
        return new ResponseEntity<>(bookService.getBookById(id), HttpStatus.OK);
    }

    @PostMapping("/edit")
    public ResponseEntity<String> editBook(@Valid @RequestBody Book book) {
        ResponseEntity<String> response;
        if (bookService.editBook(book)) {
            response = new ResponseEntity<>("Book with ID = " + book.getId() + " edited.", HttpStatus.OK);
        }
        else {
            response = new ResponseEntity<>("Library does not have a book with ID = " + book.getId() +
                    " to edit.", HttpStatus.BAD_REQUEST);
        }
        return response;
    }

    @PutMapping("/add")
    public ResponseEntity<String> addBook(@Valid @RequestBody Book book) {
        ResponseEntity<String> response;
        if (bookService.addBook(book)) {
            response = new ResponseEntity<>("Book with ID = " + book.getId() + " added successfully.", HttpStatus.OK);
        }
        else {
            response = new ResponseEntity<>("The book has not been added. Book with ID = " + book.getId() +
                    " already exists in library.", HttpStatus.BAD_REQUEST);
        }
        return response;
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteBook(@PathVariable(value = "id") @Min(value = 1) int id) {
        ResponseEntity<String> response;
        if (bookService.deleteBookById(id)) {
            response = new ResponseEntity<>("Book with ID = " + id + " removed from library.", HttpStatus.OK);
        }
        else {
            response = new ResponseEntity<>("Library does not have a book with ID = " + id + " to delete.",
                    HttpStatus.BAD_REQUEST);
        }
        return response;
    }
}
