package ru.intervale.course.utils.mappers;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.intervale.course.model.BookDTO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

/**
 * Конвертация результата запроса в БД в представление книги
 */
@Component
public class BookDTOMapper implements RowMapper<BookDTO> {

    /**
     * Конвертирует результата запроса в БД в представление книги
     * @return представление книги, сформированное из запроса к БД
     */
    @Override
    public BookDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        return BookDTO.builder()
                .id(String.valueOf(rs.getInt("ID")))
                .name(rs.getString("NAME"))
                .isbn(Arrays.asList(rs.getString("ISBN")))
                .authors(Arrays.asList(rs.getString("AUTHOR")))
                .weight(rs.getInt("WEIGHT") + " grams")
                .pageCount(rs.getInt("PAGES"))
                .price(rs.getBigDecimal("PRICE"))
                .build();
    }
}
