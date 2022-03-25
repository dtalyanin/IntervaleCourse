package ru.intervale.course.external.openlibrary.service;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;
import ru.intervale.course.model.BookDTO;

import java.io.IOException;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class OpenLibraryServiceTest {
    @Autowired
    ObjectMapper mapper;
    @Mock
    private RestTemplate template;
    @InjectMocks
    private OpenLibraryService service;

    @Test
    void getBooksByAuthorFromOpenLibrary() throws IOException {
        BookDTO book = BookDTO.builder().build();
        JsonFactory factory = mapper.getFactory();
        JsonParser parser = factory.createParser("{\"edition_key\":[\"1\", \"2\"]}");
        when(template.getForObject(anyString(), eq(JsonNode.class))).thenReturn(mapper.readTree(parser));
        when(template.getForObject(anyString(), eq(BookDTO.class))).thenReturn(book);
        assertEquals(Arrays.asList(book, book), service.getBooksByAuthor("perumov"));
    }
}