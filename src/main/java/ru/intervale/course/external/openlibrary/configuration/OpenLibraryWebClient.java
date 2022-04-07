package ru.intervale.course.external.openlibrary.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import ru.intervale.course.utils.filters.WebClientFilter;

/**
 * WebClient для обращения к API Open Library
 */
@Configuration
@ConfigurationProperties(prefix = "external.open-library")
@Data
public class OpenLibraryWebClient {
    private String baseUrl;

    /**
     * Добавляет к RestTemplate базовый URL, ErrorHandler, Interceptor для обращения к API Open Library
     * @return сконфигурированный RestTemplate
     */
    @Bean
    public WebClient webClient() {
        return WebClient.builder()
                .baseUrl(baseUrl)
                .filter(new WebClientFilter())
                .codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(1024 * 1024 * 10))
                .build();
    }
}
