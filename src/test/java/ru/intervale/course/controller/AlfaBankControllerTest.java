package ru.intervale.course.controller;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import ru.intervale.course.model.BookWithCurrencies;
import ru.intervale.course.service.impl.BookServiceImpl;

import java.util.ArrayList;
import java.util.Arrays;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AlfaBankControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Mock
    BookServiceImpl service;
    @Mock
    BookWithCurrencies bookWithCurrencies;
    @InjectMocks
    AlfaBankController alfaBankController;

    @Test
    public void testGetPriceByTitle() {
        when(service.getBooksWithCurrencies("no title")).thenReturn(new ArrayList<>());
        when(service.getBooksWithCurrencies("Eragon")).thenReturn(Arrays.asList(bookWithCurrencies, bookWithCurrencies));
        assertEquals(new ResponseEntity("No books with title 'no title' found.", HttpStatus.OK), alfaBankController.getPriceByTitle("no title"));
        assertEquals(new ResponseEntity(Arrays.asList(bookWithCurrencies, bookWithCurrencies), HttpStatus.OK), alfaBankController.getPriceByTitle("Eragon"));
    }
}
