package ru.intervale.course.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.intervale.course.model.BookWithCurrencies;
import ru.intervale.course.model.BookWithCurrencyRange;
import ru.intervale.course.service.impl.BookServiceImpl;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * Контроллер для работы с книгами из БД и курсами валют Альфа-банка
 */
@Validated
@RestController
@Tag(name = "Alfa-Bank Controller", description = "Контроллер для работы с книгами в интеграции с API Альфа-банка.")
public class AlfaBankController {
    @Autowired
    BookServiceImpl service;

    /**
     * Получить стоимость книги в различных валютах по её названию
     * @param title Название книги в БД.
     * @return Список книг с заданным названием из БД и стоимостью в различных валютах по курсу Альфа-банка
     */
    @GetMapping("/price/{title}")
    @Operation(summary = "Получить стоимость книги в различных валютах по её названию.")
    @ApiResponse(description = "Список книг с заданным названием из БД и стоимостью в различных валютах.",
            responseCode = "200", content = @Content(array = @ArraySchema(schema = @Schema(implementation = BookWithCurrencies.class))))
    public ResponseEntity getPriceByTitle(@PathVariable @Parameter(description = "Название книги в БД.") @NotBlank String title) {
        List<BookWithCurrencies> bookCurrencies = service.getBooksWithCurrencies(title);
        ResponseEntity response;
        if (bookCurrencies.size() != 0) {
            response = new ResponseEntity(bookCurrencies, HttpStatus.OK);
        }
        else {
            response = new ResponseEntity("No books with title '" + title + "' found.", HttpStatus.OK);
        }
        return response;
    }

    /**
     * Получить стоимость книги в заданном диапазоне по дням в выбранной валюте по курсу НБ РБ (выходные дни не отображаются)
     * @param title название книги для поиска
     * @param currency код валюты согласно ISO
     * @return список книг с заданным названием и диапазоном их стоимости по дням в выбранной валюте
     */
    @GetMapping("/price/{title}/{currency}")
    @Operation(summary = "Получить стоимость книги в заданном диапазоне по дням в выбранной валюте по курсу " +
            "НБ РБ (выходные дни не отображаются).")
    @ApiResponse(description = "Список книг с заданным названием и диапазоном их стоимости по дням в выбранной валюте.",
            responseCode = "200", content = @Content(array = @ArraySchema(schema = @Schema(implementation = BookWithCurrencyRange.class))))
    public ResponseEntity<?> getPriceByTitleInDynamics(
            @PathVariable @Parameter(description = "Название книги в БД.") @NotBlank String title,
            @PathVariable @Parameter(description = "Код валюты согласно ISO.") @NotBlank String currency) {
        final int period = 30;
        List<BookWithCurrencyRange> bookRangeCurrencies = service.getBookWithCurrencyRange(title, currency, period);
        ResponseEntity<?> response;
        if (bookRangeCurrencies.size() != 0) {
            if (bookRangeCurrencies.get(0).getPrices().size() != 0) {
                response = new ResponseEntity<>(bookRangeCurrencies, HttpStatus.OK);
            }
            else {
                response = new ResponseEntity<>("No currency '" + currency + "' found.", HttpStatus.OK);
            }
        }
        else {
            response = new ResponseEntity<>("No books with title '" + title + "' found.", HttpStatus.OK);
        }
        return response;
    }
}
