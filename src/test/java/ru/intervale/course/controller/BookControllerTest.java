package ru.intervale.course.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ru.intervale.course.model.Book;
import ru.intervale.course.model.enums.ErrorCode;
import ru.intervale.course.model.responses.ErrorResponse;
import ru.intervale.course.service.BookServiceImpl;

import java.math.BigDecimal;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class BookControllerTest {
    @Autowired
    ObjectMapper mapper;
    @Autowired
    private MockMvc mockMvc;
    @Mock
    BookServiceImpl service;
    @InjectMocks
    BookController controller;

    @Test
    void getBooks() {
        controller.getBooks();
        verify(service, times(1)).getBooks();
    }

    @Test
    void getBookById() throws Exception {
        controller.getBookById(2);
        verify(service, times(1)).getBookById(anyInt());
        ErrorResponse errorResponse = new ErrorResponse(ErrorCode.VALIDATION_ERROR, "getBookById.id",
                0, "ID cannot be less than 1");
        mockMvc.perform(get("/book/0")).andExpect(status().isBadRequest()).andExpect(
                content().json(mapper.writeValueAsString(errorResponse)));
    }

    @Test
    @WithMockUser(username = "editor", password = "editor", roles = "EDITOR")
    void editBook() throws Exception {
        Book book = new Book(1, "123-1-123-12345-1", "Book 1", "N. Perumov",
                100, 200, BigDecimal.valueOf(2.56));
        controller.editBook(1, book);
        verify(service, times(1)).editBook(anyInt(), any(Book.class));
        ErrorResponse errorResponse = new ErrorResponse(ErrorCode.VALIDATION_ERROR, "editBook.id",
                0, "ID cannot be less than 1");
        mockMvc.perform(post("/edit/0").contentType(MediaType.APPLICATION_JSON).content(mapper
                .writeValueAsString(book))).andExpect(status().isBadRequest())
                .andExpect(content().json(mapper.writeValueAsString(errorResponse)));
        mockMvc.perform(post("/edit/0").contentType(MediaType.APPLICATION_JSON).content(mapper
                        .writeValueAsString(book))).andExpect(status().isBadRequest())
                .andExpect(content().json(mapper.writeValueAsString(errorResponse)));
    }

    @Test
    @WithMockUser(username = "editor", password = "editor", roles = "EDITOR")
    void addBook() throws Exception {
        Book book = new Book(1, "123-1-123-12345-1", "Book 1", "Perumov",
                100, 200, BigDecimal.valueOf(2.56));
        controller.addBook(book);
        verify(service, times(1)).addBook(any(Book.class));
        ErrorResponse errorResponse = new ErrorResponse(ErrorCode.VALIDATION_ERROR, "author",
                "Perumov", "Incorrect format. Use X.X. XXXX");
        mockMvc.perform(put("/add").contentType(MediaType.APPLICATION_JSON).content(mapper
                        .writeValueAsString(book))).andExpect(status().isBadRequest())
                .andExpect(content().json(mapper.writeValueAsString(errorResponse)));
    }

    @Test
    @WithMockUser(username = "editor", password = "editor", roles = "EDITOR")
    void deleteBook() throws Exception {
        controller.deleteBook(2);
        verify(service, times(1)).deleteBookById(anyInt());
        ErrorResponse errorResponse = new ErrorResponse(ErrorCode.VALIDATION_ERROR, "deleteBook.id",
                0, "ID cannot be less than 1");
        mockMvc.perform(delete("/delete/0")).andExpect(status().isBadRequest()).andExpect(
                content().json(mapper.writeValueAsString(errorResponse)));
    }

    @Test
    void getBooksByAuthor() throws Exception {
        controller.getBooksByAuthor("perumov");
        verify(service, times(1)).getBooksByAuthor("perumov");
        ErrorResponse errorResponse = new ErrorResponse(ErrorCode.VALIDATION_ERROR, "getBooksByAuthor.author",
                "  ", "Author name for search cannot be empty");
        mockMvc.perform(get("/author/  ")).andExpect(status().isBadRequest()).andExpect(
                content().json(mapper.writeValueAsString(errorResponse)));
    }

    @Test
    void getPriceByTitle() throws Exception {
        controller.getPriceByTitle("perumov");
        verify(service, times((1))).getBooksByNameWithCurrentPrice("perumov");
        ErrorResponse errorResponse = new ErrorResponse(ErrorCode.VALIDATION_ERROR, "getPriceByTitle.title",
                "  ", "Title for search cannot be empty");
        mockMvc.perform(get("/price/  ")).andExpect(status().isBadRequest()).andExpect(
                content().json(mapper.writeValueAsString(errorResponse)));
    }

    @Test
    void getPriceByTitleInRange() throws Exception {
        controller.getPriceByTitleInRange("perumov", "USD", 30);
        verify(service, times(1)).getBookByNameWithCurrencyPriceInRange("perumov", "USD", 30);
        ErrorResponse titleErrorResponse = new ErrorResponse(ErrorCode.VALIDATION_ERROR, "getPriceByTitleInRange.title",
                "  ", "Title for search cannot be empty");
        ErrorResponse currencyEmptyErrorResponse = new ErrorResponse(ErrorCode.VALIDATION_ERROR, "getPriceByTitleInRange.currency",
                "   ", "Currency for search cannot be empty");
        ErrorResponse currencyIsoErrorResponse = new ErrorResponse(ErrorCode.VALIDATION_ERROR, "getPriceByTitleInRange.currency",
                "usdd", "Incorrect format. Use ISO format for currency (for example USD)");
        ErrorResponse minPeriodErrorResponse = new ErrorResponse(ErrorCode.VALIDATION_ERROR, "getPriceByTitleInRange.period",
                1, "Minimum period is 2 days");
        ErrorResponse maxPeriodErrorResponse = new ErrorResponse(ErrorCode.VALIDATION_ERROR, "getPriceByTitleInRange.period",
                35, "Maximum period is 30 days");
        mockMvc.perform(get("/price/  /usd?period=30")).andExpect(status().isBadRequest()).andExpect(
                content().json(mapper.writeValueAsString(titleErrorResponse)));
        mockMvc.perform(get("/price/perumov/   ?period=30")).andExpect(status().isBadRequest()).andExpect(
                content().json(mapper.writeValueAsString(currencyEmptyErrorResponse)));
        mockMvc.perform(get("/price/perumov/usdd?period=30")).andExpect(status().isBadRequest()).andExpect(
                content().json(mapper.writeValueAsString(currencyIsoErrorResponse)));
        mockMvc.perform(get("/price/perumov/usd?period=1")).andExpect(status().isBadRequest()).andExpect(
                content().json(mapper.writeValueAsString(minPeriodErrorResponse)));
        mockMvc.perform(get("/price/perumov/usd?period=35")).andExpect(status().isBadRequest()).andExpect(
                content().json(mapper.writeValueAsString(maxPeriodErrorResponse)));
    }
}