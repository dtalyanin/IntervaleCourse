package ru.intervale.course.dao;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.intervale.course.model.Book;
import ru.intervale.course.model.BookDto;

import java.util.Arrays;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class BookDaoTest {
    @Mock
    private Book book;
    @Mock
    private JdbcTemplate template;
    @InjectMocks
    private BookDao bookDao;

    @Test
    public void testGetBooks() {
        when(template.query(anyString(), any(RowMapper.class))).thenReturn(Arrays.asList(book, book));
        assertEquals(bookDao.getBooks(), Arrays.asList(book, book));
    }

    @Test
    public void testGetBookById() {
        when(template.queryForObject(anyString(), any(RowMapper.class), anyInt())).thenReturn(book);
        assertEquals(bookDao.getBookById(10), book);
    }

    @Test
    public void testDeleteBook() {
        when(template.update(anyString(), anyInt())).thenReturn(1);
        assertEquals(bookDao.deleteBook(10), 1);
    }

    @Test
    public void testAddBook() {
        when(template.update(anyString(), anyString(), anyString(), anyString(), anyInt(), anyInt(), anyInt())).thenReturn(10);
        assertEquals(bookDao.addBook(new Book(0, "978-5-041-17971-7", "New Book",
                "A.A. Kuznez", 5, 5, 5)), 10);
    }

//    @Test
//    public void testEditBook() {
//        when(template.update("UPDATE BOOKS SET NAME = ? WHERE ID = ?", new Object[]{"New Book", 2})).thenReturn(2);
//        //Request doesn't have fields for edit
//        BookDto dto = BookDto.builder().ID(1).build();
//        assertEquals(bookDao.editBook(dto), -999);
//        //Request have field "Name" for edit
//        dto = BookDto.builder().ID(2).name("New Book").build();
//        assertEquals(2, bookDao.editBook(dto));
//    }
}
