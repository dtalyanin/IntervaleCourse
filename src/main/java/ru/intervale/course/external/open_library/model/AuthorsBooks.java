package ru.intervale.course.external.open_library.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;
import ru.intervale.course.external.open_library.model.deserializer.AuthorsBooksDeserializer;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс списка Open Library ID книг автора
 */
@Data
@JsonDeserialize(using = AuthorsBooksDeserializer.class)
public class AuthorsBooks {
    private List<String> books_olid;

    public AuthorsBooks() {
        books_olid = new ArrayList<>();
    }

    public void addBook(String book_olid) {
        books_olid.add(book_olid);
    }
}
