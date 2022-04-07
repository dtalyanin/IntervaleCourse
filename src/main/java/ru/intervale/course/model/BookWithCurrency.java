package ru.intervale.course.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Map;

/**
 * Книга со стоимостью в различных валютах
 */
@Data
@AllArgsConstructor
@Schema(description = "Книга со стоимостью в различных валютах.")
public class BookWithCurrency {
    private int id;
    private String isbn;
    private String name;
    private String author;
    private int pageCount;
    private int weight;
    private BigDecimal bynPrice;
    private Map<String, BigDecimal> currencyPrices;
}
