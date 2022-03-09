package ru.intervale.course.dao;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.intervale.course.model.Book;
import ru.intervale.course.utils.mappers.BookMapper;

import java.math.BigDecimal;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class BookDaoImplTest {
    @Mock
    private JdbcTemplate template;
    @InjectMocks
    private BookDaoImpl bookDao;

    private final Book firstBook = new Book(1, "123-1-123-12345-1", "Book 1", "N. Perumov",
            100, 200, BigDecimal.valueOf(2.56));
    private final Book secondBook = new Book(2, "123-1-123-12345-2", "Book 2", "J.K. Rowling",
            100, 200, BigDecimal.valueOf(3.56));

    @Test
    void getBooks() {
        when(template.query(anyString(), any(BookMapper.class))).thenReturn(Arrays.asList(firstBook, secondBook));
        assertEquals(Arrays.asList(firstBook, secondBook), bookDao.getBooks());
    }

    @Test
    void getBookById() {
        when(template.queryForObject(anyString(), any(BookMapper.class), eq(1))).thenReturn(firstBook);
        when(template.queryForObject(anyString(), any(BookMapper.class), eq(2))).thenReturn(secondBook);
        assertEquals(firstBook, bookDao.getBookById(1));
        assertEquals(secondBook, bookDao.getBookById(2));
    }

    @Test
    void getBooksByAuthor() {
        when(template.query(anyString(), any(BookMapper.class), eq("%perumov%"))).thenReturn(Arrays.asList(firstBook, secondBook));
        assertEquals(Arrays.asList(firstBook, secondBook), bookDao.getBooksByAuthor("perumov"));
    }

    @Test
    void addBook() {
        bookDao.addBook(firstBook);
        verify(template, times(1)).update(anyString(), anyString(), anyString(),
                anyString(), anyInt(), anyInt(), any(BigDecimal.class));
    }

    @Test
    void editBook() {
        when(template.update(anyString(), anyString(), anyString(), anyString(), anyInt(), anyInt(),
                any(BigDecimal.class), eq(1))).thenReturn(1);
        when(template.update(anyString(), anyString(), anyString(), anyString(), anyInt(), anyInt(),
                any(BigDecimal.class), eq(2))).thenReturn(0);
        assertTrue(bookDao.editBook(firstBook));
        assertFalse(bookDao.editBook(secondBook));
    }

    @Test
    void deleteBookById() {
        when(template.update(anyString(), eq(1))).thenReturn(1);
        when(template.update(anyString(), eq(2))).thenReturn(0);
        assertTrue(bookDao.deleteBookById(1));
        assertFalse(bookDao.deleteBookById(2));
    }
}