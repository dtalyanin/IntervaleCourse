package ru.intervale.course.external.alfabank.service;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;
import ru.intervale.course.exception.ExternalException;
import ru.intervale.course.external.alfabank.model.NationalRate;
import ru.intervale.course.external.alfabank.model.NationalRateListResponse;
import ru.intervale.course.external.alfabank.model.Rate;
import ru.intervale.course.external.alfabank.model.RateListResponse;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
class AlfaBankServiceTest {
    @Mock
    private RestTemplate template;
    @InjectMocks
    AlfaBankService service;

    @Test
    void getTodayRates() {
        RateListResponse rateListResponse = mock(RateListResponse.class);
        Rate rate = new Rate();
        rate.setBuyIso("BYN");
        rate.setSellRate(new BigDecimal(0));
        rate.setQuantity(1);
        when(template.getForObject(anyString(), eq(RateListResponse.class))).thenReturn(rateListResponse);
        when(rateListResponse.getRates()).thenReturn(Arrays.asList(rate, rate));
        Map<String, BigDecimal> conversionRates = new HashMap<>();
        conversionRates.put(null, new BigDecimal(0));
        conversionRates.put(null, new BigDecimal(0));
        assertEquals(conversionRates, service.getTodayRates());
    }

    @Test
    void getRatesInRange() {
        Map<String, BigDecimal> conversionRates = new HashMap<>();
        List<NationalRate> rates = new ArrayList<>();
        NationalRate rate = new NationalRate();
        NationalRateListResponse nationalRates = mock(NationalRateListResponse.class);
        when(template.getForObject(anyString(), eq(NationalRateListResponse.class))).thenReturn(nationalRates);
        when(nationalRates.getRates()).thenReturn(rates);
        assertThrows(ExternalException.class, () -> service.getRatesInRange("aaa", 2));
        LocalDate now = LocalDate.now();
        rate.setIso("USD");
        rate.setCode(123);
        rate.setRate(new BigDecimal(0));
        rate.setQuantity(1);
        rate.setDate(now);
        rates.add(rate);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        conversionRates.put(now.format(formatter), new BigDecimal(0));
        assertEquals(conversionRates, service.getRatesInRange("usd", 1));
    }
}