package ru.intervale.course.external.open_library.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.intervale.course.external.open_library.model.AuthorsBooks;
import ru.intervale.course.external.open_library.model.AuthorsWorks;
import ru.intervale.course.external.open_library.model.OpenLibraryBook;
import ru.intervale.course.external.open_library.model.Work;
import ru.intervale.course.external.open_library.service.OpenLibraryService;

import java.util.ArrayList;
import java.util.List;

/**
 * Получение данных по книгам из библиотеки Open Library
 */
@Service
public class OpenLibraryServiceImpl implements OpenLibraryService {
    @Autowired
    @Qualifier("OpenLibrary")
    RestTemplate template;

    private final static String AUTHOR_SEARCH = "/search.json?author=";
    private final static String BOOK_SEARCH = "/api/books?bibkeys=OLID:";
    private final static String SEARCH_RESPONSE_FORMAT = "&jscmd=data&format=json";

    /**
     * Возвращает список всех работ по автору, содержащихся в библиотеке Open Library
     * @param author данные автора для поиска
     * @return список работ указанного автора, содержащихся в библиотеке Open Library
     */
    @Override
    public List<Work> getWorksByAuthorFromOpenLibrary(String author) {
        return template.getForObject(AUTHOR_SEARCH + author, AuthorsWorks.class).getDocs();
    }

    /**
     * Возвращает список всех книг по автору, содержащихся в библиотеке Open Library
     * @param author данные автора для поиска
     * @return список книг указанного автора, содержащихся в библиотеке Open Library
     */
    @Override
    public List<OpenLibraryBook> getBooksByAuthorFromOpenLibrary(String author) {
        //Получает список из Open Library ID по указанному автору
        AuthorsBooks authorsBooks = template.getForObject(AUTHOR_SEARCH + author, AuthorsBooks.class);
        List<OpenLibraryBook> books = new ArrayList<>();
        //Получает каждую книгу по её Open Library ID
        for (String olid: authorsBooks.getBooks_olid()) {
            OpenLibraryBook book = template.getForObject( BOOK_SEARCH+ olid +SEARCH_RESPONSE_FORMAT, OpenLibraryBook.class);
            book.setOlid(olid);
            books.add(book);
        }
        return books;
    }
}
