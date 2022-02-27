package ru.intervale.course.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.intervale.course.model.Book;
import ru.intervale.course.model.Library;
import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.Map;

@Validated
@RestController
public class BookController {
    Library library = Library.getInstance();
    @GetMapping("/books")
    public Map<Integer, Book> getBooks() {
        return library.getBooks();
    }

    @GetMapping("/book")
    public ResponseEntity getBookById(@RequestParam(value = "id") @Min(value = 1) int id) {
        ResponseEntity response;
        if (library.containsBookWithId(id)) {
            Book book = library.getBook(id);
            response = new ResponseEntity(book, HttpStatus.OK);
        }
        else {
            response = new ResponseEntity("Incorrect ID", HttpStatus.BAD_REQUEST);
        }
        return response;
    }

    @PostMapping("/edit")
    public ResponseEntity editBook(@Valid @RequestBody Book book) {
        Book editedBook = library.editBook(book);
        ResponseEntity response;
        if (editedBook != null) {
            response = new ResponseEntity("Book with ID = " + book.getID() + " edited", HttpStatus.OK);
        }
        else {
            response = new ResponseEntity("Incorrect ID for editing", HttpStatus.BAD_REQUEST);
        }
        return response;
    }

    @PutMapping("/add")
    public ResponseEntity addBook(@Valid @RequestBody Book book) {
        boolean addingResult = library.addBook(book);
        ResponseEntity response;
        if (addingResult) {
            response = new ResponseEntity("Book with ID = " + book.getID() + " added successfully", HttpStatus.OK);
        }
        else {
            response = new ResponseEntity("The book has not been added. ID = " + book.getID() +
                    " already exists in library", HttpStatus.BAD_REQUEST);
        }
        return response;
    }

    @DeleteMapping("/delete")
    public ResponseEntity deleteBook(@RequestParam(value = "id") @Min(value = 1) int id) {
        ResponseEntity response;
        if (library.containsBookWithId(id)) {
            library.deleteBook(id);
            response = new ResponseEntity("Book with ID = " + id + " removed from library", HttpStatus.OK);
        }
        else {
            response = new ResponseEntity("Incorrect ID", HttpStatus.BAD_REQUEST);
        }
        return response;
    }
}
