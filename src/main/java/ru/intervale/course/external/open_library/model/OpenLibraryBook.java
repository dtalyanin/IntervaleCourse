package ru.intervale.course.external.open_library.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.intervale.course.external.open_library.utils.OpenLibraryBookSerializer;

/**
 * Книги из библиотеки данных Open Library
 */
@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonSerialize(using = OpenLibraryBookSerializer.class)
@Schema(description = "Работа из библиотеки данных Open Library.")
public class OpenLibraryBook {
    private String title;
    private int number_of_pages_median;
    private String[] author_name;
    private String[] isbn;
}