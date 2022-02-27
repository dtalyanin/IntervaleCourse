package ru.intervale.course.external.alfa_bank.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import ru.intervale.course.external.open_library.exception.OpenLibraryErrorHandler;
import ru.intervale.course.interceptor.RestTemplateInterceptor;

/**
 * Класс конфигурации RestTemplate для обращения к API Альфа-банка
 */
@Configuration
public class AlfaBankRestTemplate {
    @Autowired
    AlfaBankConfig config;

    /**
     * Добавляет к RestTemplate базовый URL, ErrorHandler, Interceptor для обращения к API Альфа-банка
     * @return сконфигурированный RestTemplate
     */
    @Bean("AlfaBank")
    public RestTemplate restTemplate() {
        return new RestTemplateBuilder().rootUri(config.getBaseUrl()).errorHandler(new OpenLibraryErrorHandler()).
                interceptors(new RestTemplateInterceptor()).build();
    }
}
