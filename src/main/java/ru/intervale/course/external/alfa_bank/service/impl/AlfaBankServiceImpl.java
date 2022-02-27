package ru.intervale.course.external.alfa_bank.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.intervale.course.external.alfa_bank.model.RateListResponse;
import ru.intervale.course.external.alfa_bank.service.AlfaBankService;

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
}
