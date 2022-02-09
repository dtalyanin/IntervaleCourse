package ru.intervale.course.api;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import ru.intervale.course.model.OpenLibraryBook;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class OpenLibraryBookDeserializer extends StdDeserializer<OpenLibraryBook> {
    public OpenLibraryBookDeserializer() {
        this(OpenLibraryBook.class);
    }

    public OpenLibraryBookDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public OpenLibraryBook deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
        OpenLibraryBook.OpenLibraryBookBuilder builder = OpenLibraryBook.builder();
        JsonNode node = p.getCodec().readTree(p);
        node = node.get(node.fieldNames().next());
        builder.title(node.get("title").asText());
        builder.isbn(getListOfIsbn(node.get("identifiers")));
        builder.authors(getListOfAuthors(node.get("authors")));
        if (node.has("number_of_pages")) {
            builder.pageAmount(node.get("number_of_pages").asInt());
        }
        if (node.has("weight")) {
            builder.weight(node.get("weight").asText());
        }
        return builder.build();
    }

    private List<String> getListOfAuthors(JsonNode node) {
        List<String> authors = new ArrayList<>();
        for (JsonNode author:
                node) {
            authors.add(author.get("name").asText());
        }
        return authors;
    }

    private List<String> getListOfIsbn(JsonNode node) {
        List<String> isbn = new ArrayList<>();
        if (node.has("isbn_10")) {
            JsonNode isbn10 = node.get("isbn_10");
            for (int i = 0; i < isbn10.size(); i++) {
                isbn.add(isbn10.get(i).asText());
            }
        }
        if (node.has("isbn_13")) {
            JsonNode isbn13 = node.get("isbn_13");
            for (int i = 0; i < isbn13.size(); i++) {
                isbn.add(isbn13.get(i).asText());
            }
        }
        return isbn;
    }
}
