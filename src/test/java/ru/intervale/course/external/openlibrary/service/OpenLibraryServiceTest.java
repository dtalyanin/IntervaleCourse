package ru.intervale.course.external.openlibrary.service;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;
import ru.intervale.course.external.openlibrary.model.AuthorsBooks;
import ru.intervale.course.external.openlibrary.model.OpenLibraryBook;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class OpenLibraryServiceTest {
    @Mock
    private RestTemplate template;
    @Mock
    private AuthorsBooks authorsBooks;
    @InjectMocks
    private OpenLibraryService service;

    @Test
    void getBooksByAuthorFromOpenLibrary() {
        OpenLibraryBook book = OpenLibraryBook.builder().build();
        when(template.getForObject(anyString(), eq(AuthorsBooks.class))).thenReturn(authorsBooks);
        when(template.getForObject(anyString(), eq(OpenLibraryBook.class))).thenReturn(book);
        List<String> olids = new ArrayList<>();
        olids.add("123456789");
        olids.add("987654321");
        when(authorsBooks.getBooksOlid()).thenReturn(olids);
        assertEquals(Arrays.asList(book, book), service.getBooksByAuthorFromOpenLibrary("perumov"));
    }
}