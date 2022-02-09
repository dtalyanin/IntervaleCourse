package ru.intervale.course.api;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.node.ArrayNode;
import ru.intervale.course.model.AuthorsBooks;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Iterator;

public class AuthorsBooksDeserializer  extends StdDeserializer<AuthorsBooks> {
    public AuthorsBooksDeserializer() {
        this(AuthorsBooks.class);
    }

    public AuthorsBooksDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public AuthorsBooks deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
        AuthorsBooks authorsBooks = new AuthorsBooks();
        JsonNode node = p.getCodec().readTree(p);
        for (JsonNode work:
                node.get("docs")) {
            JsonNode editions = work.get("edition_key");
            for (int i = 0; i < editions.size(); i++) {
                authorsBooks.addBook(editions.get(i).asText());
            }
        }
        return authorsBooks;
    }
}
