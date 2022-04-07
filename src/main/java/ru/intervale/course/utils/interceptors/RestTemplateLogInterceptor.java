package ru.intervale.course.utils.interceptors;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;

/**
 * Интерцептор для логирования запросов, отправленных к API.
 */
@Slf4j
public class RestTemplateLogInterceptor implements ClientHttpRequestInterceptor {

    /**
     * Осуществляет логирование запроса и ответа к API
     * @param request текущий запрос
     * @param body тело запроса
     * @param execution выполение запроса
     * @return результат выполнения запроса
     */
    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        log.info("REQUEST - URL: {}, HEADERS: {}, METHOD: {}", request.getURI(), request.getHeaders(), request.getMethod());
        ClientHttpResponse response = execution.execute(request, body);
        log.info("RESPONSE - STATUS CODE: {}, HEADERS: {}", response.getStatusCode(), response.getHeaders());
        return response;
    }
}