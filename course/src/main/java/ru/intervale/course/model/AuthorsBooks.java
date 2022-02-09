package ru.intervale.course.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;
import ru.intervale.course.api.AuthorsBooksDeserializer;

import java.util.ArrayList;
import java.util.List;

@Data
@JsonDeserialize(using = AuthorsBooksDeserializer.class)
public class AuthorsBooks {
    List<String> books_olid;

    public AuthorsBooks() {
        books_olid = new ArrayList<>();
    }

    public void addBook(String book_olid) {
        books_olid.add(book_olid);
    }
}
