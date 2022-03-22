package ru.intervale.course.external.openlibrary.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import ru.intervale.course.external.openlibrary.utils.deserializer.AuthorsBooksDeserializer;

import java.util.List;

/**
 * Список ID книг из библиотеки Open Library определенного автора
 */
@Data
@AllArgsConstructor
@JsonDeserialize(using = AuthorsBooksDeserializer.class)
public class AuthorsBooks {
    List<String> booksOlid;
}
