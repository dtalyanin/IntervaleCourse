package ru.intervale.course.service;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import ru.intervale.course.dao.BookDaoImpl;
import ru.intervale.course.exception.IncorrectBookIdException;
import ru.intervale.course.external.open_library.model.OpenLibraryBook;
import ru.intervale.course.external.open_library.service.OpenLibraryServiceImpl;
import ru.intervale.course.model.Book;
import ru.intervale.course.model.enums.OperationType;
import ru.intervale.course.model.responses.BookLibraryResult;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class BookServiceImplTest {
    @Mock
    private BookDaoImpl bookDao;
    @Mock
    private OpenLibraryServiceImpl libraryService;
    @InjectMocks
    private BookServiceImpl service;

    private final Book firstBook = new Book(1, "123-1-123-12345-1", "Book 1", "N. Perumov",
            100, 200, BigDecimal.valueOf(2.56));
    private final Book secondBook = new Book(2, "123-1-123-12345-2", "Book 2", "J.K. Rowling",
            100, 200, BigDecimal.valueOf(3.56));

    @Test
    void getBooks() {
        when(bookDao.getBooks()).thenReturn(Arrays.asList(firstBook, secondBook));
        assertEquals(Arrays.asList(firstBook, secondBook), service.getBooks());
    }

    @Test
    void getBookById() {
        when(bookDao.getBookById(1)).thenReturn(firstBook);
        when(bookDao.getBookById(2)).thenReturn(null);
        assertEquals(firstBook, service.getBookById(1));
        assertThrows(IncorrectBookIdException.class, () -> service.getBookById(2));
    }

    @Test
    void addBook() {
        service.addBook(firstBook);
        verify(bookDao, times(1)).addBook(firstBook);
    }

    @Test
    void editBook() {
        when(bookDao.editBook(firstBook)).thenReturn(true);
        when(bookDao.editBook(secondBook)).thenReturn(false);
        assertEquals(new BookLibraryResult(OperationType.EDIT, "Operation completed successfully"), service.editBook(1, firstBook));
        assertThrows(IncorrectBookIdException.class, () -> service.editBook(2, secondBook));
    }

    @Test
    void deleteBookById() {
        when(bookDao.deleteBookById(1)).thenReturn(true);
        when(bookDao.deleteBookById(2)).thenReturn(false);
        assertEquals(new BookLibraryResult(OperationType.DELETE, "Operation completed successfully"), service.deleteBookById(1));
        assertThrows(IncorrectBookIdException.class, () -> service.deleteBookById(2));
    }

    @Test
    void getBooksByAuthor() {
        when(bookDao.getBooksByAuthor("nikolaev")).thenReturn(new ArrayList<>());
        when(libraryService.getBooksByAuthorFromOpenLibrary("nikolaev")).thenReturn(new ArrayList<>());
        ArrayList<Book> expectedBooks = new ArrayList<>();
        expectedBooks.add(firstBook);
        expectedBooks.add(secondBook);
        ArrayList<OpenLibraryBook> expectedOlBooks = new ArrayList<>();
        expectedOlBooks.add(new OpenLibraryBook());
        expectedOlBooks.add(new OpenLibraryBook());
        when(bookDao.getBooksByAuthor("perumov")).thenReturn(expectedBooks);
        when(libraryService.getBooksByAuthorFromOpenLibrary("perumov")).thenReturn(expectedOlBooks);
        Map<String, Object> books = new LinkedHashMap<>();
        books.put("Books from Open Library", "No books found for author 'nikolaev'.");
        books.put("Books from database", "No books found for author 'nikolaev'.");
        assertEquals(books, service.getBooksByAuthor("nikolaev"));
        books.clear();
        books.put("Books from Open Library", expectedOlBooks);
        books.put("Books from database", expectedBooks);
        assertEquals(books, service.getBooksByAuthor("perumov"));
    }
}