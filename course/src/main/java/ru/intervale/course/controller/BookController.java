package ru.intervale.course.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.intervale.course.dao.BookDao;
import ru.intervale.course.model.Book;
import ru.intervale.course.model.BookDTO;
import javax.validation.constraints.*;
import java.util.List;

@Validated
@RestController
public class BookController {
    BookDao bookDao = new BookDao();
    @GetMapping("/books")
    public ResponseEntity getBooks() {
        List<Book> books = bookDao.getBooks();
        ResponseEntity response;
        if (books.size() != 0) {
            response = new ResponseEntity(books, HttpStatus.OK);
        }
        else {
            response = new ResponseEntity("No data found.", HttpStatus.BAD_REQUEST);
        }
        return response;
    }

    @GetMapping("/book")
    public ResponseEntity getBookById(@RequestParam(value = "id") @Min(value = 1) int id) {
        Book book = bookDao.getBookById(id);
        if (book != null) {
            return new ResponseEntity(book, HttpStatus.OK);
        }
        else {
            return new ResponseEntity("Incorrect ID.", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/edit")
    public ResponseEntity editBook(@Validated @RequestBody BookDTO book) {
        int executingResult = bookDao.editBook(book);
        ResponseEntity response;
        if (executingResult > 0) {
            response = new ResponseEntity("Book with ID = " + executingResult + " changed.", HttpStatus.OK);
        }
        else if (executingResult == -999) {
            response = new ResponseEntity("No fields to edit.", HttpStatus.BAD_REQUEST);
        }
        else {
            response = new ResponseEntity("Incorrect ID.", HttpStatus.BAD_REQUEST);
        }
        return response;
    }

    @PutMapping("/add")
    public ResponseEntity addBook(@Validated @RequestBody Book book) {
        int addingResult = bookDao.addBook(book);
        ResponseEntity response;
        if (addingResult != 0) {
            response = new ResponseEntity("Book added successfully.", HttpStatus.OK);
        }
        else {
            response = new ResponseEntity("Error in expression.", HttpStatus.SERVICE_UNAVAILABLE);
        }
        return response;

    }

    @DeleteMapping("/delete")
    public ResponseEntity deleteBook(@RequestParam(value = "id") @Min(value = 1) int id) {
        int deletingResult = bookDao.deleteBook(id);
        ResponseEntity response;
        if (deletingResult !=0) {
            response = new ResponseEntity("Book with ID = " + id + " deleted.", HttpStatus.OK);
        }
        else {
            response = new ResponseEntity("Book with ID = " + id + " doesn’t exist.", HttpStatus.BAD_REQUEST);
        }
        return response;
    }
}
