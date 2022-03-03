package ru.intervale.course.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

@Data
@AllArgsConstructor
@Validated
public class BookRangeCurrency {
    @NotBlank
    private String name;
    @NonNull
    private Map<LocalDate, BigDecimal> prices;
}
