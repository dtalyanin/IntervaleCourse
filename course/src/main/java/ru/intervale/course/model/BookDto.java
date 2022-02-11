package ru.intervale.course.model;

import lombok.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;

@Builder
@Getter
public class BookDto {
    @NonNull
    @Min(1)
    private final Integer ID;
    @Pattern(regexp = "\\d{3}-\\d-\\d{3}-\\d{5}-\\d", message = "Incorrect ISBN type XXX-X-XXX-XXXXX-X")
    private String isbn;
    @Pattern(regexp = ".*[^\\p{Space}]+.*", message = "Incorrect author name")
    private String name;
    @Pattern(regexp = "\\p{IsAlphabetic}{1,}\\.(\\p{IsAlphabetic}{1,}\\.)? \\p{IsAlphabetic}{1,}", message = "Incorrect author name")
    private String author;
    @Min(1)
    private Integer pageAmount;
    @Min(1)
    private Integer weight;
    @Min(0)
    private Integer price;
}
