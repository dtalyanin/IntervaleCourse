package ru.intervale.course.external.openlibrary.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Builder;
import lombok.Data;
import ru.intervale.course.external.openlibrary.utils.deserializer.OpenLibraryBookDeserializer;

import java.util.List;

/**
 * Книга из библиотеки Open Library
 */
@Data
@Builder
@JsonDeserialize(using = OpenLibraryBookDeserializer.class)
public class OpenLibraryBook {
    private String olid;
    private String title;
    private List<String> authors;
    private Integer pagesCount;
    private String weight;
    private List<String> isbn;
}