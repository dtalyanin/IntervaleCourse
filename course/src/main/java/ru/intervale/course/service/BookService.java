package ru.intervale.course.service;

import ru.intervale.course.external.open_library.model.Work;
import ru.intervale.course.model.Book;
import ru.intervale.course.model.BookCurrency;
import ru.intervale.course.model.BookDto;
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
    public List<Book> getBooks();

    /**
     * Возвращает книгу из БД с указанным ID
     * @param id ID книги в БЗ
     * @return книга с указанным ID
     */
    public Book getBookById(int id);

    /**
     * Изменяет поля книги с ID, равным ID переданного BookDTO, согласно списку установленных полей данного DTO
     * @param book BookDTO с полями, которые необходимом изменить в БД
     * @return текстовый результат выполнения запроса
     */
    public String editBook(BookDto book);

    /**
     * Добавляет новую запись книги в БД
     * @param book добавляемая книга в БД
     * @return текстовый результат выполнения запроса
     */
    public String addBook(Book book);

    /**
     * Удаляет запись с указанным ID из БД
     * @param id ID для удаления в БД
     * @return текстовый результат выполнения запроса
     */
    public String deleteBook(int id);

    /**
     * Возвращает список всех работ по автору
     * @param author данные автора для поиска
     * @return список работ указанного автора
     */
    public List<Work> getWorksByAuthor(String author);

    /**
     * Возвращает Map (ключ - место поиска, значение - список книг) всех книг автора
     * @param author данные автора для поиска
     * @return список книг указанного
     */
    public Map<String, Object> getBooksByAuthor(String author);

    /**
     * Возвращает список из книги с заданным названием и стоимостью в различных валютах
     * @param title название книги для поиска
     * @return список  из названия книги и её стоиомсти в различных валютах
     */
    public List<BookCurrency> getBooksWithRate(String title);
}
