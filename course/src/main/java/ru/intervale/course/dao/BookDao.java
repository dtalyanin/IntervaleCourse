package ru.intervale.course.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import ru.intervale.course.model.Book;
import ru.intervale.course.model.BookDTO;
import ru.intervale.course.model.BookMapper;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

public class BookDao {
    @Autowired
    JdbcTemplate template = new JdbcTemplate(getDataSource());

    public List<Book> getBooks() {
        String sql = "SELECT * FROM BOOKS";
        return template.query(sql, new BookMapper());
    }

    public Book getBookById(int id) {
        String sql = "SELECT * FROM BOOKS WHERE ID = ?";
        return template.queryForObject(sql, new BookMapper(), new Object[] {id});
    }

    public int deleteBook(int id) {
        String sql = "DELETE FROM BOOKS WHERE ID = ?";
        return template.update(sql, id);
    }

    public int addBook(Book book) {
        String sql = "INSERT INTO BOOKS (ISBN, NAME, AUTHOR, PAGES, WEIGHT, PRICE) VALUES (?, ?, ?, ?, ?, ?)";
        return template.update(sql, book.getIsbn(), book.getName(), book.getAuthor(), book.getPageAmount(),
                book.getWeight(), book.getPrice());
    }

    public int editBook(BookDTO book) {
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
            executingResult = template.update(String.valueOf(sql),fields.toArray());
            if (executingResult != 0) {
                executingResult = book.getID();
            }
        }
        return executingResult;
    }

    @Bean
    private DataSource getDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.h2.Driver");
        dataSource.setUrl("jdbc:h2:./src/main/java/ru/intervale/course/db/db");
        dataSource.setUsername("admin");
        dataSource.setPassword("root");
        return dataSource;
    }
}
