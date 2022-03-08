package ru.intervale.course.dao;

import ru.intervale.course.model.Book;
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
    List<Book> getBooksByAuthor(String author);

    /**
     * Добавляет новую запись книги в БД
     * @param book добавляемая книга в БД
     */
    void addBook(Book book);

    /**
     * Заменяет книгу у
     * @param book книга для замены в БД
     * @return возвращает, выполнен ли запрос
     */
    boolean editBook(Book book);

    /**
     * Удаляет запись с указанным ID из БД
     * @param id ID для удаления в БД
     * @return возвращает, выполнен ли запрос
     */
    boolean deleteBookById(int id);
}
