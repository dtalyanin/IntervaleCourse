package ru.intervale.course.integration.service;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import ru.intervale.course.integration.exception.OpenLibraryErrorHandler;

@Configuration
public class OpenLibraryRestTemplate {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplateBuilder().rootUri("https://openlibrary.org").errorHandler(new OpenLibraryErrorHandler()).build();
    }
}
