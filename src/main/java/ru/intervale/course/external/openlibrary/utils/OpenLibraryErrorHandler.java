package ru.intervale.course.external.openlibrary.utils;

import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResponseErrorHandler;
import ru.intervale.course.external.openlibrary.exception.OpenLibraryException;

import java.io.IOException;
import java.net.URI;

/**
 * Обработчик ошибок ответа Open Library API.
 */
@Component
public class OpenLibraryErrorHandler implements ResponseErrorHandler {

    /**
     * Проверяет наличие ошибки в ответе
     * @param response ответ от Open Library API
     * @return true если ответ из серии 4ХХ или 5ХХ
     */
    @Override
    public boolean hasError(ClientHttpResponse response) throws IOException {
        HttpStatus.Series statusCode = response.getStatusCode().series();
        return statusCode == HttpStatus.Series.CLIENT_ERROR || statusCode == HttpStatus.Series.SERVER_ERROR;
    }

    /**
     * Генерирует ошибку в соответствии с кодом ответа от Open Library API
     * @param response ответ от Open Library API с ошибкой
     */
    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
        if (response.getStatusCode().is4xxClientError()) {
            throw  new OpenLibraryException("Unable to complete request to Open Library. Bad request.");
        } else if (response.getStatusCode().is5xxServerError()) {
            throw  new OpenLibraryException("Failed to get response from Open Library. Try again later.");
        }
    }

    /**
     * Генерирует ошибку в соответствии с кодом ответа от Open Library API с дополнительной информацией
     * @param url адрес по которому шел запрос
     * @param method метод запроса
     * @param response ответ от Open Library API с ошибкой
     */
    @Override
    public void handleError(URI url, HttpMethod method, ClientHttpResponse response) throws IOException {
        if (response.getStatusCode().is4xxClientError()) {
            throw  new OpenLibraryException("Unable to complete request to Open Library with method "
                    + method + " and URL " + url.getPath() + ". Bad request.");
        } else if (response.getStatusCode().is5xxServerError()) {
            throw  new OpenLibraryException("Failed to get response from Open Library. Try again later.");
        }
    }
}