package ru.intervale.course.external.open_library.utils;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import ru.intervale.course.external.open_library.model.OpenLibraryBook;

import java.io.IOException;

/**
 * Конвертация книги Open Library в JSON
 */
public class OpenLibraryBookSerializer extends StdSerializer<OpenLibraryBook> {
    public OpenLibraryBookSerializer() {
        this(OpenLibraryBook.class);
    }

    public OpenLibraryBookSerializer(Class<OpenLibraryBook> t) {
        super(t);
    }

    /**
     * Метод по конвертации книги Open Library в JSON
     * @param value книга Open Library для записи
     */
    @Override
    public void serialize(OpenLibraryBook value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeStartObject();
        gen.writeStringField("title", value.getTitle());
        if (value.getNumber_of_pages_median() != 0) {
            gen.writeNumberField("number_of_pages_median", value.getNumber_of_pages_median());
        }
        writeArray(value.getAuthor_name(), "author", gen);
        writeArray(value.getIsbn(), "ISBN", gen);
        gen.writeEndObject();
    }

    /**
     * Запись массива или одиночного значения в зависимости от списка значений
     */
    private void writeArray(String[] fields, String fieldName, JsonGenerator gen) throws IOException{
        if (fields != null) {
            if (fields.length == 1) {
                gen.writeStringField(fieldName, fields[0]);
            }
            else {
                gen.writeFieldName(fieldName);
                gen.writeStartArray();
                for (String field:
                        fields) {
                    gen.writeString(field);
                }
                gen.writeEndArray();
            }
        }
    }
}