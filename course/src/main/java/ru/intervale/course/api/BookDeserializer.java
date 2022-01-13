package ru.intervale.course.api;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import ru.intervale.course.model.Book;
import ru.intervale.course.model.Library;
import java.io.IOException;

public class BookDeserializer extends StdDeserializer<Book> {
    private Library library = Library.getInstance();

    public BookDeserializer() {
        this(Book.class);
    }

    public BookDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public Book deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
        JsonNode node = p.getCodec().readTree(p);
        Book book;
        if (node.has("id")) {
            return editBook(node);
        }
        else {
            return getNewBook(node);
        }
    }

    private Book getNewBook(JsonNode node) {
        String isbn = node.get("isbn").asText();
        String name = node.get("name").asText();
        String author = node.get("author").asText();
        int pageAmount = node.get("pageAmount").asInt();
        int weight = node.get("weight").asInt();
        int price = node.get("price").asInt();
        return new Book(library.getId(), isbn, name, author, pageAmount, weight, price);
    }

    private Book editBook(JsonNode node) {
        Book editedBook = null;
        int id = node.get("id").asInt();
        String isbn;
        String name;
        String author;
        int pageAmount;
        int weight;
        int price;
        if (library.getBooks().containsKey(id)) {
            Book book = library.getBooks().get(id);
            if (node.has("isbn")) {
                isbn = node.get("isbn").asText();
            }
            else {
                isbn = book.getIsbn();
            }
            if (node.has("name")) {
                name = node.get("name").asText();
            }
            else {
                name = book.getName();
            }
            if (node.has("author")) {
                author = node.get("author").asText();
            }
            else {
                author = book.getAuthor();
            }
            if (node.has("pageAmount")) {
                pageAmount = node.get("pageAmount").asInt();
            }
            else {
                pageAmount = book.getPageAmount();
            }
            if (node.has("weight")) {
                weight = node.get("weight").asInt();
            }
            else {
                weight = book.getWeight();
            }
            if (node.has("price")) {
                price = node.get("price").asInt();
            }
            else {
                price = book.getPrice();
            }
            editedBook = new Book(id, isbn, name, author, pageAmount, weight, price);
        }
        return editedBook;
    }
}
