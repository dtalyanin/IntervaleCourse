package ru.intervale.course.model.mapper;

import static org.mockito.Mockito.*;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.intervale.course.model.Book;
import java.sql.ResultSet;
import java.sql.SQLException;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class BookMapperTest {
    @Test
    public void testBookMapper() throws SQLException {
        ResultSet rs = mock(ResultSet.class);
        when(rs.getInt("ID")).thenReturn(1);
        when(rs.getString("ISBN")).thenReturn("978-5-041-17971-7");
        when(rs.getString("NAME")).thenReturn("New book");
        when(rs.getString("AUTHOR")).thenReturn("A.A. Abrams");
        when(rs.getInt("PAGES")).thenReturn(10);
        when(rs.getInt("WEIGHT")).thenReturn(20);
        when(rs.getInt("PRICE")).thenReturn(100);
        Book book = new Book(1, "978-5-041-17971-7", "New book", "A.A. Abrams", 10, 20 ,100);
        Assert.assertEquals(book, new BookMapper().mapRow(rs, 1));
    }
}
