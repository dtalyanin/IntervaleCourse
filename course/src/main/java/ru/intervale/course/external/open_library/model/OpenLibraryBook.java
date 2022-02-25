package ru.intervale.course.external.open_library.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import ru.intervale.course.external.open_library.model.deserializer.OpenLibraryBookDeserializer;
import ru.intervale.course.external.open_library.model.serializer.OpenLibraryBookSerializer;

import java.util.List;

/**
 * Книга из библиотеки данных Open Library
 */
@Data
@Builder
@JsonDeserialize(using = OpenLibraryBookDeserializer.class)
@JsonSerialize(using = OpenLibraryBookSerializer.class)
@Schema(description = "Книга из библиотеки данных Open Library.")
public class OpenLibraryBook {
    private String olid;
    private String title;
    private List<String> authors;
    private int pageAmount;
    private String weight;
    private List<String> isbn;
}
