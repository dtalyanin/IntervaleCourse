package ru.intervale.course.external.alfabank.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;
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

/**
 * Получение данных из API Альфа-банка
 */
@Service
public class AlfaBankService {
    @Autowired
    @Qualifier("AlfaBank")
    RestTemplate template;

    private static final String TODAY_RATES = "/rates";
    private static final String RATES_ON_DATE = "/nationalRates?date=";
    private static final String CURRENCY_CODE = "&currencyCode=";
    /**
     * Возвращает список курсов конвертации к 1 BYN, установленных Альфа-банком на сегоднящний день
     * @return список курсов конвертации к 1 BYN
     */
    public Map<String, BigDecimal> getTodayRates() {
        List<Rate> rates = template.getForObject(TODAY_RATES, RateListResponse.class).getRates();
        Map<String, BigDecimal> conversionRates = new HashMap<>();
        String byn = "BYN";
        for (Rate rate: rates) {
            if (rate.getBuyIso().equals(byn)) {
                conversionRates.put(rate.getSellIso(), rate.getSellRate().divide(new BigDecimal(rate.getQuantity())));
            }
        }
        return conversionRates;
    }

    /**
     * Возвращает список курсов конвертации указанной валюту к 1 BYN по курсу НБ РБ за указанный период
     * @param currency код валюты согласно ISO
     * @param period период для поиска
     * @return список курсов конвертации указанной валюту к 1 BYN по курсу НБ РБ за указанный период (для выходных дней значения будут отсутсвовать)
     */
    public Map<String, BigDecimal> getRatesInRange(String currency, int period) {
        LocalDate dateNow = LocalDate.now();
        LocalDate dateStart = dateNow.minusDays(period - 1);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        Map<String, BigDecimal> rates = new LinkedHashMap<>();
        NationalRateListResponse nationalRates = template.getForObject(RATES_ON_DATE +
                formatter.format(dateStart), NationalRateListResponse.class);
        int code = 0;
        for (NationalRate rate: nationalRates.getRates()) {
            if (rate.getIso().equalsIgnoreCase(currency)) {
                code = rate.getCode();
                rates.put(rate.getDate().format(formatter), rate.getRate().divide(new BigDecimal(rate.getQuantity())));
                break;
            }
        }
        if (code == 0) {
            throw new ExternalException("Cannot find currency with ISO = " + currency);
        }
        while (dateStart.isBefore(dateNow)) {
            dateStart = dateStart.plusDays(1);
            nationalRates = template.getForObject(RATES_ON_DATE + formatter.format(dateStart) +
                    CURRENCY_CODE + code, NationalRateListResponse.class);
            NationalRate nationalRate = nationalRates.getRates().get(0);
            rates.put(nationalRate.getDate().format(formatter), nationalRate.getRate().divide(new BigDecimal(nationalRate.getQuantity())));
        }
        return rates;
    }
}