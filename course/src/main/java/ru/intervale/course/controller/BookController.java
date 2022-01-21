package ru.intervale.course.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.intervale.course.dao.BookDao;
import ru.intervale.course.model.Book;
import ru.intervale.course.model.Library;
import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.List;

@Validated
@RestController
public class BookController {
    Library library = Library.getInstance();
    BookDao bookDao = new BookDao();
    @GetMapping("/books")
    public List<Book> getBooks() {
        return bookDao.getBooks();
    }

    @GetMapping("/book")
    public ResponseEntity getBookById(@RequestParam(value = "id") @Min(value = 1) int id) {
        Book book = bookDao.getBookById(id);
        if (book != null) {
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
        int addingResult = bookDao.addBook(book);
        ResponseEntity responseEntity;
        if (addingResult != 0) {
            responseEntity = new ResponseEntity("Book " + addingResult + " added to library", HttpStatus.OK);
        }
        else {
            responseEntity = new ResponseEntity("Incorrect ID", HttpStatus.BAD_REQUEST);
        }
        return responseEntity;

    }

    @DeleteMapping("/delete")
    public ResponseEntity deleteBook(@RequestParam(value = "id") @Min(value = 1) int id) {
        if (bookDao.deleteBook(id) != 0) {
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
