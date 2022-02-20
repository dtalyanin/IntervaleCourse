package ru.intervale.course.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.intervale.course.dao.impl.BookDaoImpl;
import ru.intervale.course.integration.model.OpenLibraryBook;
import ru.intervale.course.integration.model.Work;
import ru.intervale.course.integration.service.impl.OpenLibraryServiceImpl;
import ru.intervale.course.model.Book;
import ru.intervale.course.model.BookDto;
import ru.intervale.course.service.impl.BookServiceImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
public class BookServiceImplTest {
    @Mock
    BookDaoImpl bookDao;
    @Mock
    OpenLibraryServiceImpl libraryService;
    @Mock
    Book book;
    @Mock
    OpenLibraryBook olBook;
    @Mock
    Work work;
    @InjectMocks
    BookServiceImpl service;

    @Test
    public void testGetBooks() {
        when(bookDao.getBooks()).thenReturn(Arrays.asList(book, book, book));
        assertEquals(Arrays.asList(book, book, book), service.getBooks());
    }

    @Test
    public void testGetBookById() {
        when(bookDao.getBookById(1)).thenReturn(book);
        assertEquals(book, service.getBookById(1));
    }

    @Test
    public void testEditBook() {
        BookDto bookDtoFirst = mock(BookDto.class);
        BookDto bookDtoSecond = mock(BookDto.class);
        BookDto bookDtoThird = mock(BookDto.class);
        when(bookDao.editBook(bookDtoFirst)).thenReturn(1);
        when(bookDao.editBook(bookDtoSecond)).thenReturn(-999);
        when(bookDao.editBook(bookDtoThird)).thenReturn(0);
        assertEquals("Book with ID = 1 changed.", service.editBook(bookDtoFirst));
        assertEquals("No fields to edit.", service.editBook(bookDtoSecond));
        assertEquals("Incorrect ID.", service.editBook(bookDtoThird));
    }

    @Test
    public void testAddBook() {
        Book bookSecond = mock(Book.class);
        when(bookDao.addBook(book)).thenReturn(1);
        when(bookDao.addBook(bookSecond)).thenReturn(0);
        assertEquals("Book added successfully.", service.addBook(book));
        assertEquals("Error in expression.", service.addBook(bookSecond));
    }

    @Test
    public void testDeleteBook() {
        when(bookDao.deleteBook(1)).thenReturn(1);
        when(bookDao.deleteBook(100)).thenReturn(0);
        assertEquals("Book with ID = 1 deleted.", service.deleteBook(1));
        assertEquals("Book with ID = 100 doesn’t exist.", service.deleteBook(100));
    }

    @Test
    public void testGetWorksByAuthor() {
        when(bookDao.getBooksByAuthorAsWork("perumov")).thenReturn(Arrays.asList(work, work));
        when(bookDao.getBooksByAuthorAsWork("nikolaev")).thenReturn(new ArrayList<>());
        when(bookDao.getBooksByAuthorAsWork("rowling")).thenReturn(Arrays.asList(work, work, work));
        when(libraryService.getWorksByAuthorFromOpenLibrary("perumov")).thenReturn(new ArrayList<>());
        when(libraryService.getWorksByAuthorFromOpenLibrary("nikolaev")).thenReturn(new ArrayList<>());
        ArrayList<Work> works = new ArrayList<>();
        works.add(work);
        when(libraryService.getWorksByAuthorFromOpenLibrary("rowling")).thenReturn(works);
        ArrayList<Work> expectedWorks = new ArrayList<>();
        assertEquals(expectedWorks, service.getWorksByAuthor("nikolaev"));
        expectedWorks.add(work);
        expectedWorks.add(work);
        assertEquals(expectedWorks, service.getWorksByAuthor("perumov"));
        expectedWorks.add(work);
        expectedWorks.add(work);
        assertEquals(expectedWorks, service.getWorksByAuthor("rowling"));
    }

    @Test
    public void testGetBooksByAuthor() {
        when(bookDao.getBooksByAuthor("nikolaev")).thenReturn(new ArrayList<>());
        when(libraryService.getBooksByAuthorFromOpenLibrary("nikolaev")).thenReturn(new ArrayList<>());
        ArrayList<Book> expectedBooks = new ArrayList<>();
        expectedBooks.add(book);
        expectedBooks.add(book);
        ArrayList<OpenLibraryBook> expectedOlBooks = new ArrayList<>();
        expectedOlBooks.add(olBook);
        expectedOlBooks.add(olBook);
        when(bookDao.getBooksByAuthor("perumov")).thenReturn(expectedBooks);
        when(libraryService.getBooksByAuthorFromOpenLibrary("perumov")).thenReturn(expectedOlBooks);
        HashMap<String, Object> books = new HashMap<>();
        books.put("Books from Opel Library", "No books found for author 'nikolaev'.");
        books.put("Books from database", "No books found for author 'nikolaev'.");
        assertEquals(books, service.getBooksByAuthor("nikolaev"));
        books.clear();
        books.put("Books from Opel Library", expectedOlBooks);
        books.put("Books from database", expectedBooks);
        assertEquals(books, service.getBooksByAuthor("perumov"));
    }
}
