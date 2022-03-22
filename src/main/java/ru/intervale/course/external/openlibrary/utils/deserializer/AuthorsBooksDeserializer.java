package ru.intervale.course.external.openlibrary.utils.deserializer;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import ru.intervale.course.external.openlibrary.model.AuthorsBooks;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Конвертация JSON в объект класса AuthorsBooks
 */
public class AuthorsBooksDeserializer extends StdDeserializer<AuthorsBooks> {
    public AuthorsBooksDeserializer() {
        this(AuthorsBooks.class);
    }

    public AuthorsBooksDeserializer(Class<?> vc) {
        super(vc);
    }

    /**
     * Конвертирует JSON в объект класса AuthorsBooks
     * @return объект класса AuthorsBooks
     */
    @Override
    public AuthorsBooks deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        List<String> booksOlid = new ArrayList<>();
        JsonNode node = p.getCodec().readTree(p);
        for (JsonNode work: node.get("docs")) {
            JsonNode editions = work.get("edition_key");
            for (JsonNode edition: editions) {
                booksOlid.add(edition.asText());
            }
        }
        return new AuthorsBooks(booksOlid);
    }
}
