package ru.intervale.course.integration.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.intervale.course.integration.model.serializer.WorkSerializer;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonSerialize(using = WorkSerializer.class)
public class Work {
    private String title;
    private int number_of_pages_median;
    private String[] author_name;
    private String[] isbn;
}
