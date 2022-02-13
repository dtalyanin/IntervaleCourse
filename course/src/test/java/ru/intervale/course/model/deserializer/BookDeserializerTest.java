package ru.intervale.course.model.deserializer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.intervale.course.model.Book;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class BookDeserializerTest {

    @Test
    public void testBookDeserializer() throws JsonProcessingException {
        String json = "{\"isbn\":\"978-5-041-17971-7\",\"name\":\"new Book\",\"author\":\"A.A. Kuznez\",\"pageAmount\"" +
                ":5,\"weight\":5,\"price\":5}";
        Book book = new ObjectMapper().readValue(json, Book.class);
        Assert.assertEquals(new Book(0, "978-5-041-17971-7","new Book","A.A. Kuznez", 5, 5, 5), book);
    }

}
