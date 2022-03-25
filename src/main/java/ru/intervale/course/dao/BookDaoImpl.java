package ru.intervale.course.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.intervale.course.model.Book;
import ru.intervale.course.model.BookDTO;
import ru.intervale.course.utils.mappers.BookDTOMapper;
import ru.intervale.course.utils.mappers.BookMapper;

import java.util.*;

/**
 * Выполнение операций по обращению к БД
 */
@Repository
public class BookDaoImpl implements BookDao {
    @Autowired
    private JdbcTemplate template;
    private static final String SELECT_ALL = "SELECT * FROM BOOKS";
    private static final String SELECT_BY_ID = "SELECT * FROM BOOKS WHERE ID = ?";
    private static final String ADD = "INSERT INTO BOOKS (ISBN, NAME, AUTHOR, PAGES, WEIGHT, PRICE) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String EDIT = "UPDATE BOOKS SET ISBN = ?, NAME = ?, AUTHOR = ?, PAGES = ?, WEIGHT = ?, PRICE = ? WHERE ID = ?";
    private static final String DELETE = "DELETE FROM BOOKS WHERE ID = ?";
    private static final String SELECT_BY_AUTHOR = "SELECT * FROM BOOKS WHERE LOWER(AUTHOR) LIKE LOWER(?)";

    /**
     * Возвращает список всех книг из базы данных
     * @return список всех книг
     */
    @Override
    public List<Book> getBooks() {
        return template.query(SELECT_ALL, new BookMapper());
    }

    /**
     * Возвращает книгу из БД с указанным ID
     * @param id ID для поиска в БД
     * @return книга с указанным ID (если не найдена, возвращает null)
     */
    @Override
    public Book getBookById(int id) {
        Book book;
        try {
            book = template.queryForObject(SELECT_BY_ID, new BookMapper(), id);
        }
        catch (EmptyResultDataAccessException e) {
            book = null;
        }
        return book;
    }

    /**
     * Возвращает список книг заданного автора
     * @param author автор для поиска в БД
     * @return список книг заданного автора
     */
    @Override
    public List<BookDTO> getBooksByAuthor(String author) {
        return template.query(SELECT_BY_AUTHOR, new BookDTOMapper(), '%' + author + '%');
    }

    /**
     * Добавляет новую запись книги в БД
     * @param book добавляемая книга в БД
     */
    @Override
    public void addBook(Book book) {
        template.update(ADD,
                book.getIsbn(),
                book.getName(),
                book.getAuthor(),
                book.getPageCount(),
                book.getWeight(),
                book.getPrice());
    }

    /**
     * Заменяет книгу с указанным ID в БД на переданную в качестве параметра книгу
     * @param book книга для замены в БД
     * @return логический результат выполнения запроса
     */
    @Override
    public boolean editBook(Book book) {
        return template.update(EDIT,
                book.getIsbn(),
                book.getName(),
                book.getAuthor(),
                book.getPageCount(),
                book.getWeight(),
                book.getPrice(),
                book.getId()) == 1;
    }

    /**
     * Удаляет запись с указанным ID из БД
     * @param id ID для удаления в БД
     * @return логический результат выполнения запроса
     */
    @Override
    public boolean deleteBookById(int id) {
        return template.update(DELETE, id) == 1;
    }
}
