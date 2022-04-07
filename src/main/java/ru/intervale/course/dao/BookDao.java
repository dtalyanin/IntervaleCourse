package ru.intervale.course.dao;

import ru.intervale.course.model.Book;
import ru.intervale.course.model.BookDTO;
import ru.intervale.course.utils.mappers.BookMapper;

import java.util.List;

/**
 * Интерфейс для выполнения операций по обращению к БД
 */
public interface BookDao {
    /**
     * Возвращает список всех книг из базы данных
     * @return список всех книг
     */
    List<Book> getBooks();

    /**
     * Возвращает книгу из БД с указанным ID
     * @param id ID для поиска в БД
     * @return книга с указанным ID
     */
    Book getBookById(int id);

    /**
     * Возвращает список книг заданного автора
     * @param author автор для поиска в БД
     * @return список книг заданного автора
     */
    List<BookDTO> getBooksByAuthor(String author);

    /**
     * Возвращает список книг с заданным названием
     * @param name название для поиска в БД
     * @return список книг с заданным названием
     */
    List<Book> getBooksByName(String name);

    /**
     * Добавляет новую запись книги в БД
     * @param book добавляемая книга в БД
     */
    void addBook(Book book);

    /**
     * Заменяет книгу с указанным ID в БД на переданную в качестве параметра книгу
     * @param book книга для замены в БД
     * @return логический результат выполнения запроса
     */
    boolean editBook(Book book);

    /**
     * Удаляет запись с указанным ID из БД
     * @param id ID для удаления в БД
     * @return логический результат выполнения запроса
     */
    boolean deleteBookById(int id);
}
