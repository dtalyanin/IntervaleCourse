package ru.intervale.course.service;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.intervale.course.dao.BookDaoImpl;
import ru.intervale.course.exception.IncorrectBookIdException;
import ru.intervale.course.external.openlibrary.model.OpenLibraryBook;
import ru.intervale.course.external.openlibrary.service.OpenLibraryService;
import ru.intervale.course.model.Book;
import ru.intervale.course.model.BookDTO;
import ru.intervale.course.model.enums.OperationType;
import ru.intervale.course.model.responses.BookLibraryResult;
import ru.intervale.course.utils.mappers.BookDTOMapper;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class BookServiceImplTest {
    @Mock
    BookDTOMapper mapper;
    @Mock
    private BookDaoImpl bookDao;
    @Mock
    private OpenLibraryService libraryService;
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
        assertEquals(new ArrayList<>(), service.getBooksByAuthor("nikolaev"));
        OpenLibraryBook olBook = OpenLibraryBook.builder().build();
        BookDTO bookDTO = BookDTO.builder().build();
        when(bookDao.getBooksByAuthor("perumov")).thenReturn(Arrays.asList(firstBook, secondBook));
        when(libraryService.getBooksByAuthorFromOpenLibrary("perumov")).thenReturn(Arrays.asList(olBook));
        when(mapper.convertBookToBookDto(any(Book.class))).thenReturn(bookDTO);
        when(mapper.convertOpenLibraryBookToBookDto(any(OpenLibraryBook.class))).thenReturn(bookDTO);
        assertEquals(Arrays.asList(bookDTO, bookDTO, bookDTO), service.getBooksByAuthor("perumov"));
    }
}