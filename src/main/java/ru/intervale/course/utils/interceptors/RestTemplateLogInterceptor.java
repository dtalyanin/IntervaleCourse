package ru.intervale.course.utils.interceptors;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import ru.intervale.course.external.alfabank.utils.AlfaBankBroker;

import java.io.IOException;

/**
 * Интерцептор для логирования запросов, отправленных к API.
 */

@Component
@Slf4j
public class RestTemplateLogInterceptor implements ClientHttpRequestInterceptor {
    @Autowired
    private AlfaBankBroker broker;

    /**
     * Осуществляет логирование запроса и ответа к API
     * @param request текущий запрос
     * @param body тело запроса
     * @param execution выполение запроса
     * @return результат выполнения запроса
     */
    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        log.info("ALFA-BANK REQUEST - URL: {}, HEADERS: {}, METHOD: {}", request.getURI(), request.getHeaders(), request.getMethod());
        ClientHttpResponse response = execution.execute(request, body);
        broker.sengMessage(request.getHeaders().toString());
        log.info("ALFA-BANK RESPONSE - STATUS CODE: {}, HEADERS: {}", response.getStatusCode(), response.getHeaders());
        return response;
    }
}