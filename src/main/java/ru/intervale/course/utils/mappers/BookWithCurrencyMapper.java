package ru.intervale.course.utils.mappers;

import ru.intervale.course.model.Book;
import ru.intervale.course.model.BookWithCurrency;

import java.math.BigDecimal;
import java.util.*;

public class BookWithCurrencyMapper {
    public static BookWithCurrency convertFromBook(Book book, Map<String, BigDecimal> rates) {
        Map<String, BigDecimal> prices = new HashMap<>();
        for (Map.Entry<String, BigDecimal> rate: rates.entrySet()) {
            prices.put(rate.getKey(), book.getPrice().divide(rate.getValue(), BigDecimal.ROUND_HALF_UP).setScale(2));
        }
        return new BookWithCurrency(
                book.getId(),
                book.getIsbn(),
                book.getName(),
                book.getAuthor(),
                book.getPageCount(),
                book.getWeight(),
                book.getPrice(),
                prices);
    }
}
