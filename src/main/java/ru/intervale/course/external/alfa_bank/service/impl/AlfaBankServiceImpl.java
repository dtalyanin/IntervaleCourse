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

    /**
     * Возвращает список в виде Map (ключ - дата установления курса, значение - действующий курс НБ РБ)
     * @param currency код валюты согласно ISO
     * @param period диапазон, в котором выполнить поиск курсов валют
     * @return диапазон курсов валют по дням (для выходных дней значения могут отсутствовать)
     */
    @Override
    public Map<String, BigDecimal> getRatesInRange(String currency, int period) {
        LocalDate dateStart = LocalDate.now().minusDays(period);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        Map<String, BigDecimal> rates = new LinkedHashMap<>();
        NationalRateListResponse rateListResponse = template.getForObject("/nationalRates?date=" + formatter.format(dateStart), NationalRateListResponse.class);
        int currencyCode = 0;
        for (NationalRate rate: rateListResponse.getRates()) {
            if (rate.getIso().equalsIgnoreCase(currency)) {
                currencyCode = rate.getCode();
                rates.put(rate.getDate().format(formatter), rate.getRate());
                break;
            }
        }
        if (currencyCode != 0) {
            LocalDate dateNow = LocalDate.now();
            while (dateStart.isBefore(dateNow)) {
                dateStart = dateStart.plusDays(1);
                rateListResponse = template.getForObject("/nationalRates?date=" + formatter.format(dateStart) + "&currencyCode=" + currencyCode, NationalRateListResponse.class);
                NationalRate nationalRate = rateListResponse.getRates().get(0);
                rates.put(nationalRate.getDate().format(formatter), nationalRate.getRate());
            }
        }
        return rates;
    }
}
