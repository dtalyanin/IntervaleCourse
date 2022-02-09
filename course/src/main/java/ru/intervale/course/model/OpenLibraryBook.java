package ru.intervale.course.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Builder;
import lombok.Data;
import ru.intervale.course.api.OpenLibraryBookDeserializer;
import java.util.List;

@Data
@Builder
@JsonDeserialize(using = OpenLibraryBookDeserializer.class)

public class OpenLibraryBook {
    private String title;
    private List<String> authors;
    private int pageAmount;
    private String weight;
    private List<String> isbn;
}
