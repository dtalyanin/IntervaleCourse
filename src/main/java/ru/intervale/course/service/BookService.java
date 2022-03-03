package ru.intervale.course.service;

import ru.intervale.course.external.open_library.model.Work;
import ru.intervale.course.model.Book;
import ru.intervale.course.model.BookWithCurrencies;
import ru.intervale.course.model.BookDto;
import ru.intervale.course.model.BookWithCurrencyRange;

import java.util.List;
import java.util.Map;

/**
 * Получение данных по книгам из базы данных. Поддерживается интеграция полученных данных
 * с API Альфа-банка и библиотеки Open Library
 */

public interface BookService {
    /**
     * Возвращает список всех книг из базы данных
     * @return список всех книг
     */
    List<Book> getBooks();

    /**
     * Возвращает книгу из БД с указанным ID
     * @param id ID книги в БЗ
     * @return книга с указанным ID
     */
    Book getBookById(int id);

    /**
     * Изменяет поля книги с ID, равным ID переданного BookDTO, согласно списку установленных полей данного DTO
     * @param book BookDTO с полями, которые необходимом изменить в БД
     * @return текстовый результат выполнения запроса
     */
    String editBook(BookDto book);

    /**
     * Добавляет новую запись книги в БД
     * @param book добавляемая книга в БД
     * @return текстовый результат выполнения запроса
     */
    String addBook(Book book);

    /**
     * Удаляет запись с указанным ID из БД
     * @param id ID для удаления в БД
     * @return текстовый результат выполнения запроса
     */
    String deleteBook(int id);

    /**
     * Возвращает список всех работ по автору
     * @param author данные автора для поиска
     * @return список работ указанного автора
     */
    List<Work> getWorksByAuthor(String author);

    /**
     * Возвращает Map (ключ - место поиска, значение - список книг) всех книг автора
     * @param author данные автора для поиска
     * @return список книг указанного
     */
    Map<String, Object> getBooksByAuthor(String author);

    /**
     * Возвращает список книг с заданным названием и их стоимостью в различных валютах
     * @param title название книги для поиска
     * @return список книг с заданным названием и их стоимостью в различных валютах
     */
    List<BookWithCurrencies> getBooksWithCurrencies(String title);

    /**
     * Возвращает список книг с заданным названием и диапазоном их стоимости по дням в выбранной валюте
     * @param title название книги для поиска
     * @param currency код валюты согласно ISO
     * @param period диапазон, в котором выполнить поиск курсов валют
     * @return список книг с заданным названием и диапазоном их стоимости по дням в выбранной валюте
     */
    List<BookWithCurrencyRange> getBookWithCurrencyRange(String title, String currency, int period);
}
