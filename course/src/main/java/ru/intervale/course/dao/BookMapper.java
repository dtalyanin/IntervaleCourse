package ru.intervale.course.dao;

import org.springframework.jdbc.core.RowMapper;
import ru.intervale.course.model.Book;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BookMapper implements RowMapper<Book> {

    @Override
    public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Book(rs.getInt("ID"), rs.getString("ISBN"), rs.getString("NAME"),
                rs.getString("AUTHOR"), rs.getInt("PAGES"), rs.getInt("WEIGHT"), rs.getInt("PRICE"));
    }
}
