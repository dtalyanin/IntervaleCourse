package ru.intervale.course.external.alfa_bank.service;

import ru.intervale.course.external.alfa_bank.model.RateListResponse;

/**
 * Получение данных из API Альфа-банка
 */
public interface AlfaBankService {
    /**
     * Возвращает список актуальных курсов, установленных Альфа-банком
     * @return список курсов валют Альфа-банка
     */
    public RateListResponse getRateList();
}
