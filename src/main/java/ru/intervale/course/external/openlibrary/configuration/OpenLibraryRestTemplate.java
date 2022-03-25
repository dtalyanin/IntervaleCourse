package ru.intervale.course.external.openlibrary.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import ru.intervale.course.external.openlibrary.utils.OpenLibraryErrorHandler;

/**
 * RestTemplate для обращения к API Open Library
 */
@Configuration
@ConfigurationProperties(prefix = "external.open-library")
@Data
public class OpenLibraryRestTemplate {
    private String baseUrl;

    /**
     * Добавляет к RestTemplate базовый URL, ErrorHandler для обращения к API Open Library
     * @return сконфигурированный RestTemplate
     */
    @Bean("OpenLibrary")
    public RestTemplate restTemplate() {
        return new RestTemplateBuilder().rootUri(baseUrl).errorHandler(new OpenLibraryErrorHandler()).build();
    }
}
