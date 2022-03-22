package ru.intervale.course.external.openlibrary.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.intervale.course.external.openlibrary.model.AuthorsBooks;
import ru.intervale.course.external.openlibrary.model.OpenLibraryBook;

import java.util.ArrayList;
import java.util.List;

/**
 * Получение данных по книгам из библиотеки Open Library
 */
@Service
public class OpenLibraryService {
    @Autowired
    @Qualifier("OpenLibrary")
    RestTemplate template;

    private final static String AUTHOR_SEARCH = "/search.json?author=";
    private final static String BOOK_SEARCH = "/api/books?bibkeys=OLID:";
    private final static String SEARCH_RESPONSE_FORMAT = "&jscmd=data&format=json";

    /**
     * Возвращает список всех книг по автору, содержащихся в библиотеке Open Library
     * @param author данные автора для поиска
     * @return список книг указанного автора, содержащихся в библиотеке Open Library
     */

    public List<OpenLibraryBook> getBooksByAuthorFromOpenLibrary(String author) {
        AuthorsBooks authorsBooks = template.getForObject(AUTHOR_SEARCH + author, AuthorsBooks.class);
        List<OpenLibraryBook> books = new ArrayList<>();
        for (String olid: authorsBooks.getBooksOlid()) {
            OpenLibraryBook book = template.getForObject( BOOK_SEARCH+ olid +SEARCH_RESPONSE_FORMAT, OpenLibraryBook.class);
            book.setOlid(olid);
            books.add(book);
        }
        return books;
    }
}
