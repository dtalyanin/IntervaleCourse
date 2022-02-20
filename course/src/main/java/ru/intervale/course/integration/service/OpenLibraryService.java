package ru.intervale.course.integration.service;

import ru.intervale.course.integration.model.OpenLibraryBook;
import ru.intervale.course.integration.model.Work;


import java.util.List;

public interface OpenLibraryService {
    public List<Work> getWorksByAuthorFromOpenLibrary(String author);

    public List<OpenLibraryBook> getBooksByAuthorFromOpenLibrary(String author);
}
