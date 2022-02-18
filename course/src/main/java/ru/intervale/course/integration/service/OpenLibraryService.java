package ru.intervale.course.integration.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.intervale.course.integration.model.AuthorsBooks;
import ru.intervale.course.integration.model.AuthorsWorks;
import ru.intervale.course.integration.model.OpenLibraryBook;
import ru.intervale.course.integration.model.Work;
import java.util.ArrayList;
import java.util.List;

@Service
public class OpenLibraryService {
    @Autowired
    RestTemplate template;

    private final static String AUTHOR_SEARCH = "/search.json?author=";
    private final static String BOOK_SEARCH = "/api/books?bibkeys=OLID:";
    private final static String SEARCH_RESPONSE_FORMAT = "&jscmd=data&format=json";

    public List<Work> getWorksByAuthorFromOpenLibrary(String author) {
        return template.getForObject(AUTHOR_SEARCH + author, AuthorsWorks.class).getDocs();
    }

    public List<OpenLibraryBook> getBooksByAuthorFromOpenLibrary(String author) {
        AuthorsBooks authorsBooks = template.getForObject(AUTHOR_SEARCH + author, AuthorsBooks.class);
        List<OpenLibraryBook> books = new ArrayList<>();
        for (String olid: authorsBooks.getBooks_olid()) {
            OpenLibraryBook book = template.getForObject( BOOK_SEARCH+ olid +SEARCH_RESPONSE_FORMAT, OpenLibraryBook.class);
            book.setOlid(olid);
            books.add(book);
        }
        return books;
    }
}
