package ru.intervale.course.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import ru.intervale.course.model.Book;
import ru.intervale.course.model.BookDto;
import ru.intervale.course.service.impl.BookServiceImpl;

import java.util.ArrayList;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class BookControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Mock
    private BookServiceImpl service;
    @Mock
    private Book book;
    @Mock
    private BookDto bookDto;
    @InjectMocks
    private BookController bookController;

    @Test
    public void testGetBooks() {
        ArrayList<Book> books = new ArrayList<>();
        when(service.getBooks()).thenReturn(books);
        //when in DB no data and DAO return empty collections
        assertEquals(new ResponseEntity("No data found in data base.", HttpStatus.OK), bookController.getBooks());
        books.add(book);
        books.add(book);
        //when DB contains some books
        assertEquals(new ResponseEntity(books, HttpStatus.OK), bookController.getBooks());
        verify(service, times(2)).getBooks();
    }

    @Test
    public void testGetBookById() throws Exception {
        when(service.getBookById(1)).thenReturn(book);
        when(service.getBookById(8)).thenThrow(EmptyResultDataAccessException.class);
        //when book with ID is found in DB
        assertEquals(new ResponseEntity(book, HttpStatus.OK), bookController.getBookById(1));
        //when book with ID isn't found in DB
        assertThrows(EmptyResultDataAccessException.class, () -> bookController.getBookById(8));
        //request with invalid argument
        mockMvc.perform(get("/book?id=-100")).andExpect(status().isForbidden()).andExpect(content().
                string("getBookById.id: must be greater than or equal to 1"));
    }

    @Test
    public void testAddBook() throws Exception {
        when(service.addBook(book)).thenReturn("Book added successfully.");
        //Book added successfully
        assertEquals(new ResponseEntity("Book added successfully.", HttpStatus.OK), bookController.addBook(book));
        //Empty json
        mockMvc.perform(put("/add").content(" ")
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest()).andExpect(content().
                string("Invalid json."));
        //Incorrect argument for book
        mockMvc.perform(put("/add").content(new ObjectMapper().writeValueAsString(new Book(0, null, null,
                "A.A. Kuznez", 5, 5, 5))).contentType(MediaType.APPLICATION_JSON)).
                andExpect(status().isForbidden()).andExpect(content().string("Incorrect argument to initialization."));
    }

    @Test
    public void testDeleteBook() throws Exception {
        when(service.deleteBook(1)).thenReturn("Book with ID = 1 deleted.");
        when(service.deleteBook(2)).thenReturn("Book with ID = 2 doesn’t exist.");
        //when book with ID is found and deleted from DB
        assertEquals(new ResponseEntity("Book with ID = 1 deleted.", HttpStatus.OK), bookController.deleteBook(1));
        //when book with ID isn't foundin DB
        assertEquals(new ResponseEntity("Book with ID = 2 doesn’t exist.", HttpStatus.OK), bookController.deleteBook(2));
        //request with invalid argument
        mockMvc.perform(delete("/delete?id=-100")).andExpect(status().isForbidden()).andExpect(content().
                string("deleteBook.id: must be greater than or equal to 1"));
    }

    @Test
    public void testEditBook() throws Exception {
        when(service.editBook(bookDto)).thenReturn("Book with ID = 1 changed.");
        //Book edited successfully
        assertEquals(new ResponseEntity("Book with ID = 1 changed.", HttpStatus.OK), bookController.editBook(bookDto));
        //Book with ID doesn't exist in DB
        bookDto = BookDto.builder().ID(100).pageAmount(20).build();
        when(service.editBook(bookDto)).thenReturn("Incorrect ID.");
        assertEquals(new ResponseEntity("Incorrect ID.", HttpStatus.OK), bookController.editBook(bookDto));
        //Empty json
        mockMvc.perform(post("/edit").content(" ")
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest()).andExpect(content().
                string("Invalid json."));
        //Incorrect argument for book
        bookDto = BookDto.builder().ID(-5).pageAmount(100).build();
        mockMvc.perform(put("/add").content(new ObjectMapper().writeValueAsString(bookDto)).contentType(MediaType.APPLICATION_JSON)).
                andExpect(status().isForbidden()).andExpect(content().string("Incorrect argument to initialization."));
    }
}
