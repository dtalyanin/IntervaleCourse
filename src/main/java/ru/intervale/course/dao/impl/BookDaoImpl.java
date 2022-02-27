package ru.intervale.course.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.intervale.course.dao.BookDao;
import ru.intervale.course.model.mapper.BookMapper;
import ru.intervale.course.external.open_library.model.mapper.WorkMapper;
import ru.intervale.course.model.Book;
import ru.intervale.course.model.BookDto;
import ru.intervale.course.external.open_library.model.Work;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс для выполнения операция по обращению к БД
 */
@Repository
public class BookDaoImpl implements BookDao {
    @Autowired
    private JdbcTemplate template;

    /**
     * Возвращает список всех книг из базы данных
     * @return список всех книг
     */
    @Override
    public List<Book> getBooks() {
        String sql = "SELECT * FROM BOOKS";
        return template.query(sql, new BookMapper());
    }

    /**
     * Возвращает книгу из БД с указанным ID
     * @param id ID для поиска в БД
     * @return книга с указанным ID (если не найдена, возвращает null)
     */
    @Override
    public Book getBookById(int id) {
        String sql = "SELECT * FROM BOOKS WHERE ID = ?";
        Book book;
        try {
            book = template.queryForObject(sql, new BookMapper(), new Object[] {id});
        }
        catch (EmptyResultDataAccessException e) {
            book = null;
        }
        return book;
    }

    /**
     * Возвращает список книг с указанным текстом в названии
     * @param name название для поиска в БД
     * @return список книг с указанным текстом в названии
     */
    @Override
    public List<Book> getBooksByName(String name) {
        String sql = "SELECT * FROM BOOKS WHERE LOWER(NAME) LIKE LOWER(?)";
        return template.query(sql, new BookMapper(), '%' + name + '%');
    }

    /**
     * Возвращает список книг заданного автора
     * @param author автор для поиска в БД
     * @return список книг заданного автора
     */
    @Override
    public List<Book> getBooksByAuthor(String author) {
        String sql = "SELECT * FROM BOOKS WHERE LOWER(AUTHOR) LIKE LOWER(?)";
        return template.query(sql, new BookMapper(), new String[] {'%' + author + '%'});
    }

    /**
     * Возвращает список книг (конвертированных в класс работ) заданного автора
     * @param author автор для поиска в БД
     * @return список работ заданного автора
     */
    @Override
    public List<Work> getBooksByAuthorAsWork(String author) {
        String sql = "SELECT * FROM BOOKS WHERE LOWER(AUTHOR) LIKE LOWER(?)";
        return template.query(sql, new WorkMapper(), new String[] {'%' + author + '%'});
    }

    /**
     * Удаляет запись с указанным ID из БД
     * @param id ID для удаления в БД
     * @return текстовый результат выполнения запроса
     */
    @Override
    public int deleteBook(int id) {
        String sql = "DELETE FROM BOOKS WHERE ID = ?";
        return template.update(sql, id);
    }

    /**
     * Добавляет новую запись книги в БД
     * @param book добавляемая книга в БД
     * @return текстовый результат выполнения запроса
     */
    @Override
    public int addBook(Book book) {
        String sql = "INSERT INTO BOOKS (ISBN, NAME, AUTHOR, PAGES, WEIGHT, PRICE) VALUES (?, ?, ?, ?, ?, ?)";
        return template.update(sql, book.getIsbn(), book.getName(), book.getAuthor(), book.getPageAmount(),
                book.getWeight(), book.getPrice());
    }

    /**
     * Изменяет поля книги с ID, равным ID переданного BookDTO, согласно списку установленных полей данного DTO
     * @param book BookDTO с полями, которые необходимом изменить в БД
     * @return текстовый результат выполнения запроса
     */
    @Override
    public int editBook(BookDto book) {
        String update = "UPDATE BOOKS SET ";
        //значение, если нет полей для изменения
        int executingResult = -999;
        List fields = new ArrayList();
        //формируется запрос в БД
        StringBuilder sql = new StringBuilder(update);
        if (book.getIsbn() != null) {
            sql.append("ISBN = ?,");
            fields.add(book.getIsbn());
        }
        if (book.getName() != null) {
            sql.append("NAME = ?,");
            fields.add(book.getName());
        }
        if (book.getAuthor() != null) {
            sql.append("AUTHOR = ?,");
            fields.add(book.getAuthor());
        }
        if (book.getPageAmount() != null) {
            sql.append("PAGES = ?,");
            fields.add(book.getPageAmount());
        }
        if (book.getWeight() != null) {
            sql.append("WEIGHT = ?,");
            fields.add(book.getWeight());
        }
        if (book.getPrice() != null) {
            sql.append("PRICE = ?,");
            fields.add(book.getPrice());
        }
        if (!update.equals(sql.toString())) {
            sql.deleteCharAt(sql.length() - 1);
            sql.append(" WHERE ID = ?");
            fields.add(book.getID());
            executingResult = template.update(String.valueOf(sql), fields.toArray());
            //если в результате запроса была изменена запись, то возвращаем ID измененной книги
            if (executingResult != 0) {
                executingResult = book.getID();
            }
        }
        return executingResult;
    }
}
