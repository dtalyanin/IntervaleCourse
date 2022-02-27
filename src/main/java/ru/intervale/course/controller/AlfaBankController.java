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
import ru.intervale.course.model.BookCurrency;
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
            responseCode = "200", content = @Content(array = @ArraySchema(schema = @Schema(implementation = BookCurrency.class))))
    public ResponseEntity getPriceByTitle(@PathVariable @Parameter(description = "Название книги в БД.") @NotBlank String title) {
        List< BookCurrency> bookCurrencies = service.getBooksWithRate(title);
        ResponseEntity response;
        if (bookCurrencies.size() != 0) {
            response = new ResponseEntity(bookCurrencies, HttpStatus.OK);
        }
        else {
            response = new ResponseEntity("No books with title '" + title + "' found.", HttpStatus.OK);
        }
        return response;
    }
}
