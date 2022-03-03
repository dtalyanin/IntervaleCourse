package ru.intervale.course.external.alfa_bank.service;

import ru.intervale.course.external.alfa_bank.model.RateListResponse;

import java.math.BigDecimal;
import java.util.Map;

/**
 * Получение данных из API Альфа-банка
 */
public interface AlfaBankService {
    /**
     * Возвращает список актуальных курсов, установленных Альфа-банком
     * @return список курсов валют Альфа-банка
     */
    RateListResponse getRateList();

    /**
     * Возвращает список в виде Map (ключ - дата установления курса, значение - действующий курс НБ РБ)
     * @param currency код валюты согласно ISO
     * @param period диапазон, в котором выполнить поиск курсов валют
     * @return диапазон курсов валют по дням (для выходных дней значения могут отсутствовать)
     */
    Map<String, BigDecimal> getRatesInRange(String currency, int period);
}
