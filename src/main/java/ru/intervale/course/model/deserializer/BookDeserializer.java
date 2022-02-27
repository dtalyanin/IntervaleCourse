package ru.intervale.course.model.deserializer;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import ru.intervale.course.model.Book;
import java.io.IOException;

/**
 * Конвертация JSON к классу книги
 */
public class BookDeserializer extends StdDeserializer<Book> {

    public BookDeserializer() {
        this(Book.class);
    }

    public BookDeserializer(Class<?> vc) {
        super(vc);
    }

    /**
     * Метод по конвертации JSON в класс книги
     * @return книга, сконвертированная из JSON
     */
    @Override
    public Book deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
        JsonNode node = p.getCodec().readTree(p);
        String isbn = node.get("isbn").asText();
        String name = node.get("name").asText();
        String author = node.get("author").asText();
        int pageAmount = node.get("pageAmount").asInt();
        int weight = node.get("weight").asInt();
        int price = node.get("price").asInt();
        return new Book(0, isbn, name, author, pageAmount, weight, price);
    }
}
