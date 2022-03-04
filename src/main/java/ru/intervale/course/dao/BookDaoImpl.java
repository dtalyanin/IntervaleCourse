package ru.intervale.course.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.intervale.course.model.Book;
import ru.intervale.course.utils.mappers.BookMapper;

import java.util.*;

@Repository
public class BookDaoImpl implements BookDao {
    @Autowired
    private JdbcTemplate template;
    private static final String SELECT_ALL = "SELECT * FROM BOOKS";
    private static final String SELECT_BY_ID = "SELECT * FROM BOOKS WHERE ID = ?";
    private static final String ADD = "INSERT INTO BOOKS (ISBN, NAME, AUTHOR, PAGES, WEIGHT, PRICE) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String EDIT = "UPDATE BOOKS SET ISBN = ?, NAME = ?, AUTHOR = ?, PAGES = ?, WEIGHT = ?, PRICE = ? WHERE ID = ?";
    private static final String DELETE = "DELETE FROM BOOKS WHERE ID = ?";

    @Override
    public List<Book> getBooks() {
        return template.query(SELECT_ALL, new BookMapper());
    }

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

    @Override
    public boolean deleteBookById(int id) {
        return template.update(DELETE, id) == 1;
    }
}
