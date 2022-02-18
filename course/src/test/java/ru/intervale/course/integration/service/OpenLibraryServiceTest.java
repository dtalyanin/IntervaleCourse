package ru.intervale.course.integration.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;
import ru.intervale.course.dao.BookDao;
import ru.intervale.course.integration.model.AuthorsBooks;
import ru.intervale.course.integration.model.AuthorsWorks;
import ru.intervale.course.integration.model.OpenLibraryBook;
import ru.intervale.course.integration.model.Work;
import ru.intervale.course.model.Book;

import java.util.*;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class OpenLibraryServiceTest {
    @Mock
    RestTemplate template;
    @Mock
    BookDao bookDao;
    @Mock
    OpenLibraryBook openLibraryBook;
    @Mock
    AuthorsWorks authorsWorks;
    @Mock
    Book book;
    @Mock
    AuthorsBooks authorsBooks;
    @InjectMocks
    OpenLibraryService service;

    @Test
    public void testGetWorksByAuthor() {
        when(template.getForObject(anyString(), any())).thenReturn(authorsWorks);
        when(authorsWorks.getDocs()).thenReturn(new ArrayList<Work>());
        when(bookDao.getBooksByAuthorAsWork("perumov")).thenReturn(new ArrayList<Work>());
        assertEquals(new ArrayList<Work>(), service.getWorksByAuthorFromOpenLibrary("perumov"));
    }

    @Test
    public void testGetBooksByAuthor() {
//        when(template.getForObject(anyString(), eq(AuthorsBooks.class))).thenReturn(authorsBooks);
//        when(template.getForObject(anyString(), eq(OpenLibraryBook.class))).thenReturn(openLibraryBook);
//        when(authorsBooks.getBooks_olid()).thenReturn(Arrays.asList(new String[] {"111", "222", "333"}));
//        when(bookDao.getBooksByAuthor("perumov")).thenReturn(Arrays.asList(new Book[] {book, book}));
//        Map<String, Object> books = service.getBooksByAuthor("perumov");
//        Map<String, Object> booksExpected = new HashMap<>();
//        booksExpected.put("Books from Opel Library", Arrays.asList(new OpenLibraryBook[] {openLibraryBook, openLibraryBook, openLibraryBook}));
//        booksExpected.put("Books from database", Arrays.asList(new Book[] {book, book}));
//        assertEquals(booksExpected, books);
    }
}
