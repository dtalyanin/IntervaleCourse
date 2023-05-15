package ru.intervale.course.external.openlibrary.service;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import ru.intervale.course.model.BookDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * Получение данных по книгам из библиотеки Open Library
 */
@Service
@AllArgsConstructor
public class OpenLibraryService {
    @Autowired
    private WebClient webClient;

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
        return Flux.fromIterable(openLibraryBookIds).flatMap(id -> webClient.get()
                .uri(BOOK_SEARCH + id + SEARCH_RESPONSE_FORMAT)
                .retrieve().bodyToMono(BookDTO.class).doOnNext(bookDTO -> bookDTO.setId(id))).collectList().block();
    }

    /**
     * Возвращает список из ID книг указанного автора в библиотеке Open Library
     * @param author данные автора для поиска
     * @return список ID всех книг автора
     */
    private List<String> getOpenLibraryBookIds(String author) {
        JsonNode node = webClient.get().uri(AUTHOR_SEARCH + author).retrieve().bodyToMono(JsonNode.class).block();
        List<String> openLibraryBookIds = new ArrayList<>();
        node.findValues("edition_key").forEach(arrayNode -> arrayNode.forEach(id -> openLibraryBookIds.add(id.asText())));
        return openLibraryBookIds;
    }
}
