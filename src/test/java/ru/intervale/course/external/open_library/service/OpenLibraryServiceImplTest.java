package ru.intervale.course.external.open_library.service;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;
import ru.intervale.course.external.open_library.model.AuthorsBooks;
import ru.intervale.course.external.open_library.model.OpenLibraryBook;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class OpenLibraryServiceImplTest {
    @Mock
    private RestTemplate template;
    @Mock
    private AuthorsBooks authorsBooks;
    @InjectMocks
    private OpenLibraryServiceImpl service;

    @Test
    void getBooksByAuthorFromOpenLibrary() {
        OpenLibraryBook firstBook = new OpenLibraryBook();
        OpenLibraryBook secondBook = new OpenLibraryBook();
        when(template.getForObject(anyString(), eq(AuthorsBooks.class))).thenReturn(authorsBooks);
        when(authorsBooks.getDocs()).thenReturn(Arrays.asList(firstBook, secondBook));
        assertEquals(Arrays.asList(firstBook, secondBook), service.getBooksByAuthorFromOpenLibrary("perumov"));
    }
}