package ru.intervale.course.utils.deserializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import ru.intervale.course.model.Book;

import java.io.IOException;
import java.math.BigDecimal;

/**
 * Конвертация JSON в объект класса Book
 */
public class BookDeserializer extends StdDeserializer<Book> {

    public BookDeserializer() {
        this(Book.class);
    }

    public BookDeserializer(Class<?> vc) {
        super(vc);
    }

    /**
     * Конвертирует JSON в объект класса Book
     * @return объект класса Book, сконвертированный из JSON
     */
    @Override
    public Book deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        JsonNode node = p.getCodec().readTree(p);
        String isbn = getStringFromField(node, "isbn");
        String name = getStringFromField(node, "name");
        String author = getStringFromField(node, "author");
        int pageCount = getIntFromField(node, "pageCount");
        int weight = getIntFromField(node, "weight");
        BigDecimal price = getBigDecimalFromField(node, "price");
        return new Book(0, isbn, name, author, pageCount, weight, price);
    }

    private String getStringFromField(JsonNode node, String field) {
        JsonNode childNode = node.get(field);
        return childNode == null || childNode.isNull() ? null : childNode.asText();
    }

    private int getIntFromField(JsonNode node, String field) {
        JsonNode childNode = node.get(field);
        return childNode == null || childNode.isNull() ? 0 : childNode.asInt();
    }

    private BigDecimal getBigDecimalFromField(JsonNode node, String field) {
        JsonNode childNode = node.get(field);
        return childNode == null || childNode.isNull() ? null : childNode.decimalValue();
    }
}