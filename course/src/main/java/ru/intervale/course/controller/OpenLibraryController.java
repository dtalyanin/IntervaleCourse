package ru.intervale.course.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import ru.intervale.course.dao.BookDao;
import ru.intervale.course.model.*;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@Validated
public class OpenLibraryController {
    RestTemplate template = new RestTemplate();

    @Autowired
    BookDao bookDao;

    @GetMapping("/works:{author}")
    public  ResponseEntity getWorksByAuthor(@PathVariable @NotBlank String author) {
        List<Work> works = template.getForObject("http://openlibrary.org/search.json?author=" +
                author, AuthorsWorks.class).getDocs();
        return new ResponseEntity(works, HttpStatus.OK);
    }

    @GetMapping("/books:{author}")
    public  ResponseEntity getBooksByAuthor(@PathVariable @NotBlank String author) {
        HashMap<String, Object> books = new HashMap<>();
        List<OpenLibraryBook> booksFromOl = getBooksByAuthorFromOpenLibrary(author);
        List<Book> booksFromDb = getBooksByAuthorFromDb(author);
        String noBooksFound = "No books found for " + author + ".";
        books.put("Books from Opel Library", booksFromOl.size() != 0 ? booksFromOl : noBooksFound);
        books.put("Books from database", booksFromDb.size() != 0 ? booksFromDb : noBooksFound);
        return new ResponseEntity(books, HttpStatus.OK);
    }

    private List<Book> getBooksByAuthorFromDb(String author) {
        return bookDao.getBooksByAuthor(author);
    }

    private List<OpenLibraryBook> getBooksByAuthorFromOpenLibrary(String author) {
        AuthorsBooks authorsBooks = template.getForObject("http://openlibrary.org/search.json?author=" +
                author, AuthorsBooks.class);
        List<OpenLibraryBook> books = new ArrayList<>();
        for (String olid: authorsBooks.getBooks_olid()) {
            OpenLibraryBook book = template.getForObject("http://openlibrary.org/api/books?bibkeys=OLID:" + olid +
                    "&jscmd=data&format=json", OpenLibraryBook.class);
            book.setOlid(olid);
            books.add(book);
        }
        return books;
    }
}
