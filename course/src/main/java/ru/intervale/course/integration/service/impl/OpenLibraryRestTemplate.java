package ru.intervale.course.integration.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import ru.intervale.course.integration.configuration.OpenLibraryConfig;
import ru.intervale.course.integration.exception.OpenLibraryErrorHandler;

@Configuration
public class OpenLibraryRestTemplate {
    @Autowired
    OpenLibraryConfig config;

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplateBuilder().rootUri(config.getBaseUrl()).errorHandler(new OpenLibraryErrorHandler()).build();
    }
}
