package ru.intervale.course.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.intervale.course.dao.BookDao;
import ru.intervale.course.model.mapper.BookMapper;
import ru.intervale.course.integration.model.mapper.WorkMapper;
import ru.intervale.course.model.Book;
import ru.intervale.course.model.BookDto;
import ru.intervale.course.integration.model.Work;

import java.util.ArrayList;
import java.util.List;

@Repository
public class BookDaoImpl implements BookDao {
    @Autowired
    private JdbcTemplate template;

    @Override
    public List<Book> getBooks() {
        String sql = "SELECT * FROM BOOKS";
        return template.query(sql, new BookMapper());
    }

    @Override
    public Book getBookById(int id) {
        String sql = "SELECT * FROM BOOKS WHERE ID = ?";
        return template.queryForObject(sql, new BookMapper(), new Object[] {id});
    }

    @Override
    public List<Book> getBooksByAuthor(String author) {
        String sql = "SELECT * FROM BOOKS WHERE LOWER(AUTHOR) LIKE LOWER(?)";
        return template.query(sql, new BookMapper(), new String[] {'%' + author + '%'});
    }

    @Override
    public List<Work> getBooksByAuthorAsWork(String author) {
        String sql = "SELECT * FROM BOOKS WHERE LOWER(AUTHOR) LIKE LOWER(?)";
        return template.query(sql, new WorkMapper(), new String[] {'%' + author + '%'});
    }

    @Override
    public int deleteBook(int id) {
        String sql = "DELETE FROM BOOKS WHERE ID = ?";
        return template.update(sql, id);
    }

    @Override
    public int addBook(Book book) {
        String sql = "INSERT INTO BOOKS (ISBN, NAME, AUTHOR, PAGES, WEIGHT, PRICE) VALUES (?, ?, ?, ?, ?, ?)";
        return template.update(sql, book.getIsbn(), book.getName(), book.getAuthor(), book.getPageAmount(),
                book.getWeight(), book.getPrice());
    }

    @Override
    public int editBook(BookDto book) {
        String update = "UPDATE BOOKS SET ";
        int executingResult = -999;
        List fields = new ArrayList();
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
            if (executingResult != 0) {
                executingResult = book.getID();
            }
        }
        return executingResult;
    }
}
