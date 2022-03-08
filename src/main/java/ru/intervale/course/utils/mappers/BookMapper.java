package ru.intervale.course.utils.mappers;

import org.springframework.jdbc.core.RowMapper;
import ru.intervale.course.model.Book;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Конвертация результата запроса в БД в класс книги
 */
public class BookMapper implements RowMapper<Book> {

    /**
     * Конвертация результата запроса в БД в класс книги
     * @return книга, сформированная из запроса к БД
     */
    @Override
    public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Book(
                rs.getInt("ID"),
                rs.getString("ISBN"),
                rs.getString("NAME"),
                rs.getString("AUTHOR"),
                rs.getInt("PAGES"),
                rs.getInt("WEIGHT"),
                rs.getBigDecimal("PRICE"));
    }
}