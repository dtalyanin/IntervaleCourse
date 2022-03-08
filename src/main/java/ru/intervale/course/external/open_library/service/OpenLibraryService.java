package ru.intervale.course.external.open_library.service;

import ru.intervale.course.external.open_library.model.OpenLibraryBook;

import java.util.List;

/**
 * Получение данных по книгам из библиотеки Open Library
 */
public interface OpenLibraryService {
    /**
     * Возвращает список всех книг по автору, содержащихся в библиотеке Open Library
     * @param author данные автора для поиска
     * @return список книг указанного автора, содержащихся в библиотеке Open Library
     */
    List<OpenLibraryBook> getBooksByAuthorFromOpenLibrary(String author);
}
