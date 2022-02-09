package ru.intervale.course.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import ru.intervale.course.model.AuthorsBooks;
import ru.intervale.course.model.OpenLibraryBook;

import javax.validation.constraints.NotBlank;

@RestController
public class OpenLibraryController {
    RestTemplate template = new RestTemplate();

    @GetMapping("/{author}")
    public AuthorsBooks getBooksByAuthorFromOpenApi(@PathVariable @NotBlank String author) {
        AuthorsBooks authorsBooks = template.getForEntity("http://openlibrary.org/search.json?author=" + author, AuthorsBooks.class).getBody();
        return authorsBooks;
    }

    @GetMapping("/api")
    public OpenLibraryBook getBooksByAuthor2() {
        OpenLibraryBook res = template.getForObject("http://openlibrary.org/api/books?bibkeys=OLID:OL22440379M&jscmd=data&format=json", OpenLibraryBook.class);
        return res;
    }

//    @GetMapping("/author:{author}")
//    public ResponseEntity getBooksByAuthor(@PathVariable @NotBlank String author) {
//        List<Book> books = bookDao.getBooksByAuthor(author);
//        ResponseEntity response;
//        if (books.size() > 0) {
//            response = new ResponseEntity(books, HttpStatus.OK);
//        }
//        else {
//            response = new ResponseEntity("No books found for author " + author, HttpStatus.BAD_REQUEST);
//        }
//        return response;
//    }
}
