package ru.intervale.course.external.alfa_bank.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Класс содержит конфигурацию базового URL для интеграции с API Альфа-банка
 */

@Configuration
@ConfigurationProperties(prefix = "integration.alfa-bank")
@Data
public class AlfaBankConfig {
    private String baseUrl;
}
