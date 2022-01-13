package ru.intervale.course.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.validation.annotation.Validated;
import ru.intervale.course.api.BookDeserializer;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;


@Data
@AllArgsConstructor
@JsonDeserialize(using = BookDeserializer.class)
@Validated
public class Book {
    private final int ID;
    @Pattern(regexp = "\\d{3}-\\d-\\d{3}-\\d{5}-\\d", message = "Incorrect ISBN type XXX-X-XXX-XXXXX-X")
    private String isbn;
    @NotBlank
    private String name;
    @Pattern(regexp = "\\p{IsAlphabetic}{1,}\\.(\\p{IsAlphabetic}{1,}\\.)? \\p{IsAlphabetic}{1,}", message = "Incorrect author name")
    private String author;
    @Min(1)
    private int pageAmount;
    @Min(1)
    private int weight;
    @Min(0)
    private int price;
}
