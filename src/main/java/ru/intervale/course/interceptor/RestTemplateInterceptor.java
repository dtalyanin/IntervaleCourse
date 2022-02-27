package ru.intervale.course.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;

/**
 * Интерцептор к запросам, отправленным к интегрированным API.
 */
public class RestTemplateInterceptor implements ClientHttpRequestInterceptor {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * Осуществляет логирование запроса и ответа к интегрированным API
     * @param request текущий запрос
     * @param body тело запроса
     * @param execution выполение запроса
     * @return результат выполнения запроса
     */
    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        logger.debug("REQUEST - URL: {}, HEADERS: {}, METHOD: {}", request.getURI(), request.getHeaders(), request.getMethod());
        ClientHttpResponse response = execution.execute(request, body);
        logger.debug("RESPONSE - STATUS CODE: {}, HEADERS: {}", response.getStatusCode(), response.getHeaders());
        return response;
    }
}
