package ru.intervale.course.integration.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;
import ru.intervale.course.integration.model.deserializer.AuthorsBooksDeserializer;

import java.util.ArrayList;
import java.util.List;

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
