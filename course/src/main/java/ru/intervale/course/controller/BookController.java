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
    public Map getBooks() {
        return library.getBooks();
    }

    @GetMapping("/book")
    public ResponseEntity getBookById(@RequestParam(value = "id") @Min(value = 1) int id) {
        if (containsId(id)) {
            Book book = library.getBook(id);
            return new ResponseEntity(book, HttpStatus.OK);
        }
        else {
            return new ResponseEntity("Incorrect ID", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/edit")
    public ResponseEntity editBook(@Valid @RequestBody Book book) {
        library.addBook(book);
        return new ResponseEntity(book,HttpStatus.ACCEPTED);
    }

    @PutMapping("/add")
    public ResponseEntity addBook(@Valid @RequestBody Book book) {
            library.addBook(book);
            return new ResponseEntity(book, HttpStatus.OK);

    }

    @DeleteMapping("/delete")
    public ResponseEntity deleteBook(@RequestParam(value = "id") @Min(value = 1) int id) {
        if (containsId(id)) {
            library.deleteBook(id);
            return new ResponseEntity("Book with ID = " + id + " removed from library", HttpStatus.OK);
        }
        else {
            return new ResponseEntity("Incorrect ID", HttpStatus.BAD_REQUEST);
        }
    }

    private boolean containsId(int id) {
        return library.getBooks().containsKey(id);
    }
}
