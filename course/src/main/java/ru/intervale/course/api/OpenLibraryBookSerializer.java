package ru.intervale.course.api;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import ru.intervale.course.model.OpenLibraryBook;

import java.io.IOException;
import java.util.List;

public class OpenLibraryBookSerializer extends StdSerializer<OpenLibraryBook> {
    public OpenLibraryBookSerializer() {
        this(OpenLibraryBook.class);
    }

    public OpenLibraryBookSerializer(Class<OpenLibraryBook> t) {
        super(t);
    }

    @Override
    public void serialize(OpenLibraryBook value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeStartObject();
        gen.writeStringField("OLID", value.getOlid());
        gen.writeStringField("title", value.getTitle());
        List<String> authors = value.getAuthors();
        if (authors.size() == 1) {
            gen.writeStringField("author", authors.get(0));
        }
        else {
            gen.writeFieldName("authors");
            gen.writeStartArray();
            for (String author:
                 authors) {
                gen.writeString(author);
            }
            gen.writeEndArray();
        }
        if (value.getPageAmount() != 0) {
            gen.writeNumberField("pageAmount", value.getPageAmount());
        }
        if (value.getWeight() != null) {
            gen.writeStringField("weight", value.getWeight());
        }
        gen.writeEndObject();
    }
}
