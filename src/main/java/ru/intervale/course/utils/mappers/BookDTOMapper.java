package ru.intervale.course.utils.mappers;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.intervale.course.model.Book;
import ru.intervale.course.model.BookDTO;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

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
        Map<String, BigDecimal> price = new LinkedHashMap<>();
        price.put("BYN", rs.getBigDecimal("PRICE"));
        return BookDTO.builder()
                .id(String.valueOf(rs.getInt("ID")))
                .name(rs.getString("NAME"))
                .isbn(Arrays.asList(rs.getString("ISBN")))
                .authors(Arrays.asList(rs.getString("AUTHOR")))
                .weight(rs.getInt("WEIGHT") + " grams")
                .pageCount(rs.getInt("PAGES"))
                .price(price)
                .build();
    }

    public static BookDTO convertFromBook(Book book, Map<String, BigDecimal> rates) {
        Map<String, BigDecimal> prices = new LinkedHashMap<>();
        prices.put("BYN", book.getPrice());
        for (Map.Entry<String, BigDecimal> rate : rates.entrySet()) {
            prices.put(rate.getKey(), book.getPrice().divide(rate.getValue(), BigDecimal.ROUND_HALF_UP).setScale(2));
        }
        return BookDTO.builder()
                .id(String.valueOf(book.getId()))
                .name(book.getName())
                .isbn(Arrays.asList(book.getIsbn()))
                .authors(Arrays.asList(book.getAuthor()))
                .pageCount(book.getPageCount())
                .weight(book.getWeight() + " grams")
                .pageCount(book.getPageCount())
                .price(prices).build();
    }
}
