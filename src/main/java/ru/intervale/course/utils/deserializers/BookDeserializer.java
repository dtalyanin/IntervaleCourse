package ru.intervale.course.utils.deserializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import ru.intervale.course.model.Book;

import java.io.IOException;

public class BookDeserializer extends StdDeserializer<Book> {

    public BookDeserializer() {
        this(Book.class);
    }

    public BookDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public Book deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        JsonNode node = p.getCodec().readTree(p);
        String isbn = getStringFromField(node, "isbn");
        String name = getStringFromField(node, "name");
        String author = getStringFromField(node, "author");
        int pageCount = getIntFromField(node, "pageCount");
        int weight = getIntFromField(node, "weight");
        int price = getIntFromField(node, "price");
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
}