package ru.intervale.course.controller;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
import ru.intervale.course.external.open_library.model.Work;
import ru.intervale.course.service.impl.BookServiceImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class OpenLibraryControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Mock
    BookServiceImpl service;
    @Mock
    Work work;
    @InjectMocks
    OpenLibraryController controller;

    @Test
    public void testGetWorksByAuthor() throws Exception {
        when(service.getWorksByAuthor("aaaa")).thenReturn(new ArrayList<>());
        when(service.getWorksByAuthor("perumov")).thenReturn(Arrays.asList(new Work[] {work, work}));
        List<Work> works = new ArrayList<>();
        works.add(work);
        works.add(work);
        assertEquals(new ResponseEntity("No books found for author \'aaaa\'.", HttpStatus.OK), controller.getWorksByAuthor("aaaa"));
        assertEquals(new ResponseEntity(works, HttpStatus.OK), controller.getWorksByAuthor("perumov"));
        mockMvc.perform(get("/works:")).andExpect(status().isForbidden()).andExpect(content().
                string("getWorksByAuthor.author: must not be blank"));
    }

    @Test
    public void testGetBooksByAuthor() throws Exception {
        when(service.getBooksByAuthor("aaaa")).thenReturn(new HashMap<String, Object>());
        assertEquals(new ResponseEntity(new HashMap<String, Object>(), HttpStatus.OK), controller.getBooksByAuthor("aaaa"));
        mockMvc.perform(get("/books:")).andExpect(status().isForbidden()).andExpect(content().
                string("getBooksByAuthor.author: must not be blank"));
    }
}
