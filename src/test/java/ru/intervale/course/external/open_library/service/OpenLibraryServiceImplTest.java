package ru.intervale.course.external.open_library.service;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;
import ru.intervale.course.dao.impl.BookDaoImpl;
import ru.intervale.course.external.open_library.model.AuthorsBooks;
import ru.intervale.course.external.open_library.model.AuthorsWorks;
import ru.intervale.course.external.open_library.model.OpenLibraryBook;
import ru.intervale.course.external.open_library.model.Work;
import ru.intervale.course.external.open_library.service.impl.OpenLibraryServiceImpl;
import ru.intervale.course.model.Book;

import java.util.ArrayList;
import java.util.Arrays;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class OpenLibraryServiceImplTest {
    @Mock
    RestTemplate template;
    @Mock
    BookDaoImpl bookDao;
    @Mock
    OpenLibraryBook openLibraryBook;
    @Mock
    AuthorsWorks authorsWorks;
    @Mock
    Book book;
    @Mock
    AuthorsBooks authorsBooks;
    @InjectMocks
    OpenLibraryServiceImpl service;

    @Test
    public void testGetWorksByAuthor() {
        when(template.getForObject(anyString(), any())).thenReturn(authorsWorks);
        when(authorsWorks.getDocs()).thenReturn(new ArrayList<Work>());
        when(bookDao.getBooksByAuthorAsWork("perumov")).thenReturn(new ArrayList<Work>());
        assertEquals(new ArrayList<Work>(), service.getWorksByAuthorFromOpenLibrary("perumov"));
    }

    @Test
    public void testGetBooksByAuthor() {
        when(template.getForObject(anyString(), eq(AuthorsBooks.class))).thenReturn(authorsBooks);
        when(template.getForObject(anyString(), eq(OpenLibraryBook.class))).thenReturn(openLibraryBook);
        ArrayList<String> olids = new ArrayList<>();
        olids.add("123456789");
        olids.add("987654321");
        when(authorsBooks.getBooks_olid()).thenReturn(olids);
        when(bookDao.getBooksByAuthor("perumov")).thenReturn(Arrays.asList(new Book[] {book, book}));
        assertEquals(Arrays.asList(openLibraryBook, openLibraryBook), service.getBooksByAuthorFromOpenLibrary("perumov"));
    }
}
