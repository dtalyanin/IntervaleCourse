package ru.intervale.course.integration.model.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import ru.intervale.course.integration.model.Work;

import java.io.IOException;

public class WorkSerializer extends StdSerializer<Work> {
    public WorkSerializer() {
        this(Work.class);
    }

    public WorkSerializer(Class<Work> t) {
        super(t);
    }

    @Override
    public void serialize(Work value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeStartObject();
        gen.writeStringField("title", value.getTitle());
        if (value.getNumber_of_pages_median() != 0) {
            gen.writeNumberField("number_of_pages_median", value.getNumber_of_pages_median());
        }
        writeArray(value.getAuthor_name(), "author", gen);
        writeArray(value.getIsbn(), "ISBN", gen);
        gen.writeEndObject();
    }

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
