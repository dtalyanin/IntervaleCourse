package ru.intervale.course.external.open_library.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Класс содержит конфигурацию базового URL для интеграции с API Open Library
 */

@Configuration
@ConfigurationProperties(prefix = "integration.open-library")
@Data
public class OpenLibraryConfig {
    private String baseUrl;
}
