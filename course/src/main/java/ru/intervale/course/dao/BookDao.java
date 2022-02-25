package ru.intervale.course.dao;

import ru.intervale.course.external.open_library.model.Work;
import ru.intervale.course.model.Book;
import ru.intervale.course.model.BookDto;
import java.util.List;

/**
 * Интерфейс для выполнения операция по обращению к БД
 */
public interface BookDao {
    /**
     * Возвращает список всех книг из базы данных
     * @return список всех книг
     */
    public List<Book> getBooks();

    /**
     * Возвращает книгу из БД с указанным ID
     * @param id ID для поиска в БД
     * @return книга с указанным ID
     */
    public Book getBookById(int id);

    /**
     * Возвращает список книг с указанным текстом в названии
     * @param name название для поиска в БД
     * @return список книг с указанным текстом в названии
     */
    public List<Book> getBooksByName(String name);

    /**
     * Возвращает список книг заданного автора
     * @param author автор для поиска в БД
     * @return список книг заданного автора
     */
    public List<Book> getBooksByAuthor(String author);

    /**
     * Возвращает список книг (конвертированных в класс работ) заданного автора
     * @param author автор для поиска в БД
     * @return список работ заданного автора
     */
    public List<Work> getBooksByAuthorAsWork(String author);

    /**
     * Удаляет запись с указанным ID из БД
     * @param id ID для удаления в БД
     * @return текстовый результат выполнения запроса
     */
    public int deleteBook(int id);

    /**
     * Добавляет новую запись книги в БД
     * @param book добавляемая книга в БД
     * @return текстовый результат выполнения запроса
     */
    public int addBook(Book book);

    /**
     * Изменяет поля книги с ID, равным ID переданного BookDTO, согласно списку установленных полей данного DTO
     * @param book BookDTO с полями, которые необходимом изменить в БД
     * @return текстовый результат выполнения запроса
     */
    public int editBook(BookDto book);
}
