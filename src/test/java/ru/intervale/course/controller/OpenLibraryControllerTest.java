package ru.intervale.course.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import ru.intervale.course.model.enums.ErrorCode;
import ru.intervale.course.model.responses.ErrorResponse;
import ru.intervale.course.service.BookServiceImpl;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class OpenLibraryControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Mock
    BookServiceImpl service;
    @InjectMocks
    OpenLibraryController controller;

    @Test
    void getBooksByAuthor() throws Exception {
       controller.getBooksByAuthor("perumov");
       verify(service, times(1)).getBooksByAuthor("perumov");
       ErrorResponse errorResponse = new ErrorResponse(ErrorCode.VALIDATION_ERROR, "getBooksByAuthor.author",
               "  ", "Author name for search cannot be empty");
       mockMvc.perform(get("/author/  ")).andExpect(status().isBadRequest()).andExpect(
               content().json(new ObjectMapper().writeValueAsString(errorResponse)));
    }
}