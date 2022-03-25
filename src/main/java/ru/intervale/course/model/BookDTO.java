package ru.intervale.course.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import ru.intervale.course.utils.deserializers.BookDTODeserializer;

import java.math.BigDecimal;
import java.util.List;

/**
 * Представление книги
 */
@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonDeserialize(using = BookDTODeserializer.class)
@Schema(description = "Представление книги")
public class BookDTO {
    private String id;
    private String name;
    private List<String> isbn;
    private List<String> authors;
    private Integer pageCount;
    private String weight;
    private BigDecimal price;
}
