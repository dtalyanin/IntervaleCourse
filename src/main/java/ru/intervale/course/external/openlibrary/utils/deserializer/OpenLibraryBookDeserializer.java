package ru.intervale.course.external.openlibrary.utils.deserializer;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import ru.intervale.course.external.openlibrary.model.OpenLibraryBook;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Конвертация JSON в объект книги класса из OpenLibraryBook
 */
public class OpenLibraryBookDeserializer extends StdDeserializer<OpenLibraryBook> {
    public OpenLibraryBookDeserializer() {
        this(OpenLibraryBook.class);
    }

    public OpenLibraryBookDeserializer(Class<?> vc) {
        super(vc);
    }

    /**
     * Конвертирует JSON в объект книги класса из OpenLibraryBook
     * @return объект книги класса из OpenLibraryBook
     */
    @Override
    public OpenLibraryBook deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        OpenLibraryBook.OpenLibraryBookBuilder builder = OpenLibraryBook.builder();
        JsonNode node = p.getCodec().readTree(p);
        node = node.get(node.fieldNames().next());
        builder.title(node.get("title").asText());
        builder.isbn(getListOfIsbn(node.get("identifiers")));
        builder.authors(getListOfAuthors(node.get("authors")));
        if (node.has("number_of_pages")) {
            builder.pagesCount(node.get("number_of_pages").asInt());
        }
        if (node.has("weight")) {
            builder.weight(node.get("weight").asText());
        }
        return builder.build();
    }

    /**
     * Получение списка авторов книги из JSON
     * @return список авторов
     */
    private List<String> getListOfAuthors(JsonNode node) {
        List<String> authors = new ArrayList<>();
        for (JsonNode author: node) {
            authors.add(author.get("name").asText());
        }
        return authors;
    }

    /**
     * Получение списка ISBN книги из JSON
     * @return список ISBN
     */
    private List<String> getListOfIsbn(JsonNode node) {
        List<String> isbnList = new ArrayList<>();
        if (node.has("isbn_10")) {
            JsonNode isbn10 = node.get("isbn_10");
            for (JsonNode isbn: isbn10) {
                isbnList.add(isbn.asText());
            }
        }
        if (node.has("isbn_13")) {
            JsonNode isbn13 = node.get("isbn_13");
            for (JsonNode isbn: isbn13) {
                isbnList.add(isbn.asText());
            }
        }
        return isbnList;
    }
}