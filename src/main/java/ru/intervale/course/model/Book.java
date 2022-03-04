package ru.intervale.course.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.validation.annotation.Validated;
import ru.intervale.course.utils.deserializers.BookDeserializer;

import javax.validation.constraints.*;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@Validated
@JsonDeserialize(using = BookDeserializer.class)
public class Book {
    @Min(value = 0, message = "ID cannot be less than 0")
    private int id;
    @NotEmpty(message = "ISBN cannot be empty")
    @Pattern(regexp = "\\d{3}-\\d-\\d{3}-\\d{5}-\\d", message = "Incorrect format. Use XXX-X-XXX-XXXXX-X")
    private String isbn;
    @NotBlank(message = "Book name cannot be empty")
    private String name;
    @NotBlank(message = "Author name cannot be empty")
    @Pattern(regexp = "\\p{IsAlphabetic}+\\.(\\p{IsAlphabetic}+\\.)* \\p{IsAlphabetic}+", message = "Incorrect format. Use X.X. XXXX")
    private String author;
    @Min(value = 1, message = "Page cannot be less than 1")
    private int pageCount;
    @Min(value = 1, message = "Weight cannot be less than 1")
    private int weight;
    @NotNull(message = "Price cannot be null")
    @Digits(integer = 7, fraction = 2, message = "Incorrect format. Use ХХXXXXX.XX")
    private BigDecimal price;
}
