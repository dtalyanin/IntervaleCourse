package ru.intervale.course.external.open_library.model;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.intervale.course.external.open_library.model.mapper.WorkMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class WorkMapperTest {
    @Test
    public void testWorkMapper() throws SQLException {
        ResultSet rs = mock(ResultSet.class);
        when(rs.getString("NAME")).thenReturn("New work");
        when(rs.getInt("PAGES")).thenReturn(5);
        when(rs.getString("AUTHOR")).thenReturn("Perumov");
        when(rs.getString("ISBN")).thenReturn("123456789");
        Work work = new Work("New work", 5, new String[]{"Perumov"}, new String[]{"123456789"});
        Assert.assertEquals(work, new WorkMapper().mapRow(rs, 1));
    }
}
