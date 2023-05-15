package ru.intervale.course.external.openlibrary.service;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import okhttp3.mockwebserver.Dispatcher;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.reactive.function.client.WebClient;
import ru.intervale.course.exception.ExternalException;
import ru.intervale.course.model.BookDTO;
import ru.intervale.course.utils.filters.WebClientFilter;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class OpenLibraryServiceTest {
    @Autowired
    ObjectMapper mapper;
    private OpenLibraryService service;
    private MockWebServer server;

    @BeforeEach
    void setUp() {
        server = new MockWebServer();
        service = new OpenLibraryService(WebClient
                .builder()
                .baseUrl(server.url("/").toString())
                .filter(new WebClientFilter())
                .build());
    }

    @Test
    void getBooksByAuthorFromOpenLibrary() {
        server.setDispatcher(new Dispatcher() {
            @SneakyThrows
            @NotNull
            @Override
            public MockResponse dispatch(@NotNull RecordedRequest request) {

                MockResponse response = new MockResponse();
                switch (request.getPath()) {
                    case "/search.json?author=perumov":
                        JsonFactory factory = mapper.getFactory();
                        JsonParser parser = factory.createParser("{\"edition_key\":[\"1\", \"2\"]}");
                        response.setBody(mapper.writeValueAsString(mapper.readTree(parser)))
                            .addHeader("Content-Type", "application/json");
                        break;
                    case "/api/books?bibkeys=OLID:1&jscmd=data&format=json":
                        response.setBody("{\"OLID:OL1\": {\"title\": \"title\", \"authors\": [{\"name\": \"first\"}], \"identifiers\": {}}}")
                                .addHeader("Content-Type", "application/json");
                        break;
                    case "/api/books?bibkeys=OLID:2&jscmd=data&format=json":
                        response.setBody("{\"OLID:OL2\": {\"title\": \"title2\", \"authors\": [{\"name\": \"second\"}], \"identifiers\": {}}}")
                                .addHeader("Content-Type", "application/json");
                        break;
                    case "/search.json?author=aaa":
                        response.setResponseCode(404);
                        break;
                }
                return response;
            }
        });
        assertTrue(service.getBooksByAuthor("perumov").containsAll(Arrays.asList(
                BookDTO.builder().id("1").name("title").authors(Arrays.asList("first")).isbn(new ArrayList<>()).build(),
                BookDTO.builder().id("2").name("title2").authors(Arrays.asList("second")).isbn(new ArrayList<>()).build())));
        assertThrows(ExternalException.class, () -> service.getBooksByAuthor("aaa"));
    }
}