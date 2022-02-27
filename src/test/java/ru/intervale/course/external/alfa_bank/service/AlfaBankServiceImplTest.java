package ru.intervale.course.external.alfa_bank.service;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;
import ru.intervale.course.external.alfa_bank.model.RateListResponse;
import ru.intervale.course.external.alfa_bank.service.impl.AlfaBankServiceImpl;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class AlfaBankServiceImplTest {
    @Mock
    RestTemplate template;
    @InjectMocks
    AlfaBankServiceImpl alfaBankService;

    @Test
    public void testGetRateList() {
        when(template.getForObject("/rates", RateListResponse.class)).thenReturn(new RateListResponse());
        assertEquals(new RateListResponse(), alfaBankService.getRateList());
    }
}
