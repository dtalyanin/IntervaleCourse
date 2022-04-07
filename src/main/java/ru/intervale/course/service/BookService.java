package ru.intervale.course.service;

import ru.intervale.course.model.Book;
import ru.intervale.course.model.BookDTO;
import ru.intervale.course.model.BookWithCurrency;
import ru.intervale.course.model.responses.BookLibraryResult;

import java.util.List;

public interface BookService {
    /**
     * Возвращает список всех книг из базы данных
     * @return список всех книг
     */
    List<Book> getBooks();

    /**
     * Возвращает книгу из БД с указанным ID
     * @param id ID книги в БД
     * @return книга с указанным ID
     */
    Book getBookById(int id);

    /**
     * Добавляет новую запись книги в БД
     * @param book добавляемая книга в БД
     * @return результат выполнения запроса
     */
    BookLibraryResult addBook(Book book);

    /**
     * Заменяет книгу с указанным ID в БД на переданную в качестве параметра книгу
     * @param book книга для замены в БД
     * @return результат выполнения запроса
     */
    BookLibraryResult editBook(int id, Book book);

    /**
     * Удаляет запись с указанным ID из БД
     * @param id ID для удаления в БД
     * @return результат выполнения запроса
     */
    BookLibraryResult deleteBookById(int id);

    /**
     * Возвращает список всех книг автора из БД и Open Library
     * @param author данные автора для поиска
     * @return список книг указанного автора
     */
    List<BookDTO> getBooksByAuthor(String author);

    /**
     * Возвращает список книг с заданным названием и стоимостью в различных валютах согласно курсам на сегодняшний день,
     * получаемым из API Альфа-банка
     * @param title название книги для поиска
     * @return список книг с заданным названием и стоимостью в различных валютах на сегодняшний день
     */
    List<BookDTO> getBooksByNameWithCurrentPrice(String title);

    /**
     * Возвращает список книг с заданным названием и диапазоном их стоимости по дням в выбранной валюте
     * @param title название книги для поиска
     * @param currency код валюты согласно ISO
     * @param period диапазон, в котором выполнить поиск курсов валют
     * @return список книг с заданным названием и диапазоном их стоимости по дням в выбранной валюте
     */
    List<BookDTO> getBookByNameWithCurrencyPriceInRange(String title, String currency, int period);
}
