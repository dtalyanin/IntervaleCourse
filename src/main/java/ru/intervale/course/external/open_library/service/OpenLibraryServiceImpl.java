package ru.intervale.course.external.open_library.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.intervale.course.external.open_library.model.AuthorsBooks;
import ru.intervale.course.external.open_library.model.OpenLibraryBook;

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

    /**
     * Возвращает список всех книг по автору, содержащихся в библиотеке Open Library
     * @param author данные автора для поиска
     * @return список книг указанного автора, содержащихся в библиотеке Open Library
     */
    @Override
    public List<OpenLibraryBook> getBooksByAuthorFromOpenLibrary(String author) {
        return template.getForObject(AUTHOR_SEARCH + author, AuthorsBooks.class).getDocs();
    }
}
