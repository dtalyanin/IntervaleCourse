package ru.intervale.course.external.open_library.service;

import ru.intervale.course.external.open_library.model.OpenLibraryBook;
import ru.intervale.course.external.open_library.model.Work;


import java.util.List;

/**
 * Получение данных по книгам из библиотеки Open Library
 */

public interface OpenLibraryService {
    /**
     * Возвращает список всех работ по автору, содержащихся в библиотеке Open Library
     * @param author данные автора для поиска
     * @return список работ указанного автора, содержащихся в библиотеке Open Library
     */
    public List<Work> getWorksByAuthorFromOpenLibrary(String author);

    /**
     * Возвращает список всех книг по автору, содержащихся в библиотеке Open Library
     * @param author данные автора для поиска
     * @return список книг указанного автора, содержащихся в библиотеке Open Library
     */
    public List<OpenLibraryBook> getBooksByAuthorFromOpenLibrary(String author);
}
