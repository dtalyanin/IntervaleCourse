package ru.intervale.course.external.alfa_bank.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.intervale.course.external.alfa_bank.model.NationalRate;
import ru.intervale.course.external.alfa_bank.model.NationalRateListResponse;
import ru.intervale.course.external.alfa_bank.model.RateListResponse;
import ru.intervale.course.external.alfa_bank.service.AlfaBankService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Получение данных из API Альфа-банка
 */
@Service
@ComponentScan
public class AlfaBankServiceImpl implements AlfaBankService {
    @Autowired
    @Qualifier("AlfaBank")
    RestTemplate template;

    /**
     * Возвращает список актуальных курсов, установленных Альфа-банком
     * @return список курсов валют Альфа-банка
     */
    @Override
    public RateListResponse getRateList() {
        return template.getForObject("/rates", RateListResponse.class);
    }

    public Map<LocalDate, BigDecimal> getRatesInRange(String currency) {
        LocalDate date = LocalDate.now().minusDays(14);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        Map<LocalDate, BigDecimal> rates = new LinkedHashMap<>();
        NationalRateListResponse rateListResponse = template.getForObject("/nationalRates?date=" + formatter.format(date), NationalRateListResponse.class);
        int currencyCode = 0;
        for (NationalRate rate: rateListResponse.getRates()) {
            if (rate.getIso().equals(currency)) {
                currencyCode = rate.getCode();
                rates.put(rate.getDate(), rate.getRate());
                break;
            }
        }
        if (currencyCode != 0) {
            while (date.isAfter(LocalDate.now())) {
                date = date.plusDays(1);
                rateListResponse = template.getForObject("/nationalRates?date=" + formatter.format(date) + "&currencyCode=" + currencyCode, NationalRateListResponse.class);
                NationalRate nationalRate = rateListResponse.getRates().get(0);
                rates.put(nationalRate.getDate(), nationalRate.getRate());
            }
        }
        return rates;
    }
}
