package ru.intervale.course.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Builder;
import lombok.Data;
import ru.intervale.course.api.OpenLibraryBookDeserializer;
import ru.intervale.course.api.OpenLibraryBookSerializer;

import java.util.List;

@Data
@Builder
@JsonDeserialize(using = OpenLibraryBookDeserializer.class)
@JsonSerialize(using = OpenLibraryBookSerializer.class)

public class OpenLibraryBook {
    private String olid;
    private String title;
    private List<String> authors;
    private int pageAmount;
    private String weight;
    private List<String> isbn;
}
