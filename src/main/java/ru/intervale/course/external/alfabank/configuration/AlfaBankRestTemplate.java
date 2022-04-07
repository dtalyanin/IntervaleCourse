package ru.intervale.course.external.alfabank.configuration;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import ru.intervale.course.external.alfabank.utils.AlfaBankErrorHandler;
import ru.intervale.course.utils.interceptors.RestTemplateLogInterceptor;

/**
 * RestTemplate для обращения к API Альфа-банка
 */
@Configuration
@ConfigurationProperties(prefix = "external.alfa-bank")
@Data
public class AlfaBankRestTemplate {
    private String baseUrl;
    @Autowired
    private RestTemplateLogInterceptor interceptor;

    /**
     * Добавляет к RestTemplate базовый URL, ErrorHandler, Interceptor для обращения к API Альфа-банка
     * @return сконфигурированный RestTemplate
     */
    @Bean("AlfaBank")
    public RestTemplate restTemplate() {
        return new RestTemplateBuilder()
                .rootUri(baseUrl)
                .errorHandler(new AlfaBankErrorHandler())
                .interceptors(interceptor)
                .build();
    }
}
