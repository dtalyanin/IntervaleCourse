package ru.intervale.course.utils.deserializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import ru.intervale.course.model.BookDTO;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Конвертация JSON из запроса к Open Library в объект представления книги
 */
public class BookDTODeserializer extends StdDeserializer<BookDTO> {
    public BookDTODeserializer() {
        this(BookDTO.class);
    }

    public BookDTODeserializer(Class<?> vc) {
        super(vc);
    }

    /**
     * Конвертирует JSON из запроса к Open Library в объект представления книги
     * @return объект представления книги
     */
    @Override
    public BookDTO deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        JsonNode node = p.getCodec().readTree(p);
        node = node.get(node.fieldNames().next());
        return BookDTO.builder()
                .name(node.has("title") ? node.get("title").asText() : "untitled")
                .isbn(getListOfIsbn(node.get("identifiers")))
                .authors(getListOfAuthors(node.get("authors")))
                .pageCount(node.has("number_of_pages")? node.get("number_of_pages").asInt() : null)
                .weight(node.has("weight") ? node.get("weight").asText() : null)
                .build();
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