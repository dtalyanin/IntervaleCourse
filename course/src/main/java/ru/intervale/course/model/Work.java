package ru.intervale.course.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Work {
    private String title;
    private int number_of_pages_median;
    private String[] author_name;
    private String[] isbn;
}
