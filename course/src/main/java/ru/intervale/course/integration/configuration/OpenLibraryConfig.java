package ru.intervale.course.integration.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "open-library")
@Data
public class OpenLibraryConfig {
    private String baseUrl;
}
