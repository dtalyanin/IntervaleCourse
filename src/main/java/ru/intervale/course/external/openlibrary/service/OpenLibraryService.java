package ru.intervale.course.external.openlibrary.service;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.intervale.course.model.BookDTO;

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
    public List<BookDTO> getBooksByAuthor(String author) {
        List<String> openLibraryBookIds = getOpenLibraryBookIds(author);
        List<BookDTO> books = new ArrayList<>();
        for (String olId: openLibraryBookIds) {
            BookDTO book = template.getForObject( BOOK_SEARCH+ olId +SEARCH_RESPONSE_FORMAT, BookDTO.class);
            book.setId(olId);
            books.add(book);
        }
        return books;
    }

    /**
     * Возвращает список из ID книг указанного автора в библиотеке Open Library
     * @param author данные автора для поиска
     * @return список ID всех книг автора
     */
    private List<String> getOpenLibraryBookIds(String author) {
        JsonNode node = template.getForObject(AUTHOR_SEARCH + author, JsonNode.class);
        List<String> openLibraryBookIds = new ArrayList<>();
        node.findValues("edition_key").forEach(arrayNode -> arrayNode.forEach(id -> openLibraryBookIds.add(id.asText())));
        return openLibraryBookIds;
    }
}
