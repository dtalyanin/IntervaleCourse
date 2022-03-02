package ru.intervale.course.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.*;

@Data
@AllArgsConstructor
@Validated
public class Book {
    @Min(0)
    private int id;
    @NotEmpty(message = "ISBN cannot be empty")
    @Pattern(regexp = "\\d{3}-\\d-\\d{3}-\\d{5}-\\d", message = "Incorrect ISBN format XXX-X-XXX-XXXXX-X")
    private String isbn;
    @NotBlank
    private String name;
    @NotEmpty(message = "Author name cannot be empty")
    @Pattern(regexp = "\\p{IsAlphabetic}+\\.(\\p{IsAlphabetic}+\\.)? \\p{IsAlphabetic}+", message = "Incorrect author format X.X. XXXX")
    private String author;
    @Min(1)
    private int pageCount;
    @Min(1)
    private int weight;
    @Min(1)
    private int price;
}
