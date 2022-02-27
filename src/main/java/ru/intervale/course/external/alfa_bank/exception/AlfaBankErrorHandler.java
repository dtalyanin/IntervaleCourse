package ru.intervale.course.external.alfa_bank.exception;

import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.IOException;
import java.net.URI;

/**
 * Класс для определения, есть ли в конкретном ответе API Альфа-банка ошибка или нет.
 */
@Component
public class AlfaBankErrorHandler implements ResponseErrorHandler {

    /**
     * Проверяет наличие ошибки в ответе
     * @param response ответ от API Альфа-банка
     * @return true если ответ из серии 4ХХ или 5ХХ
     */
    @Override
    public boolean hasError(ClientHttpResponse response) throws IOException {
        HttpStatus.Series statusCode = response.getStatusCode().series();
        return statusCode == HttpStatus.Series.CLIENT_ERROR || statusCode == HttpStatus.Series.SERVER_ERROR;
    }

    /**
     * Генерация ошибки в соответствии с кодом ответа от API Альфа-банка
     * @param response ответ от API Альфа-банка с ошибкой
     */
    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
        if (response.getStatusCode().is4xxClientError()) {
            throw  new AlfaBankException("Unable to complete request to Alfa-Bank Public API. Bad request.");
        } else if (response.getStatusCode().is5xxServerError()) {
            throw  new AlfaBankException("Failed to get response from Alfa-Bank Public API. Try again later.");
        }
    }

    /**
     * Генерация ошибки в соответствии с кодом ответа от API Альфа-банка с дополнительной информацией
     * @param url адрес по которому шел запрос
     * @param method метод запроса
     * @param response ответ от API Альфа-банка с ошибкой
     */
    @Override
    public void handleError(URI url, HttpMethod method, ClientHttpResponse response) throws IOException {
        if (response.getStatusCode().is4xxClientError()) {
            throw  new AlfaBankException("Unable to complete request to Alfa-Bank Public API with method " + method + " and URL " + url.getPath() + ". Bad request.");
        } else if (response.getStatusCode().is5xxServerError()) {
            throw  new AlfaBankException("Failed to get response from Alfa-Bank Public API. Try again later.");
        }
    }
}
