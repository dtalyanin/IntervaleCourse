package ru.intervale.course.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.util.Map;

/**
 * Класс представления книги c заданным названием и диапазоном стоимости в выбранной валюте по дням
 */

@Data
@AllArgsConstructor
@Validated
@Schema(description = "Диапазон стоимости в выбранной валюте для книги с указанным названием.")
public class BookWithCurrencyRange {
    @NotBlank
    private String name;
    @NonNull
    private Map<String, BigDecimal> prices;
}
