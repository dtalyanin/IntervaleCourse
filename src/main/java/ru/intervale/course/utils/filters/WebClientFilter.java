package ru.intervale.course.utils.filters;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.ExchangeFunction;
import reactor.core.publisher.Mono;
import ru.intervale.course.exception.ExternalException;

@Component
@Slf4j
public class WebClientFilter implements ExchangeFilterFunction{

    @Override
    public Mono<ClientResponse> filter(ClientRequest request, ExchangeFunction next) {
        log.info("OPEN LIBRARY REQUEST - URL: {}, METHOD: {}", request.url(), request.method());
        return next.exchange(request).doOnNext(response -> {
            HttpStatus status = response.statusCode();
            if (status.is2xxSuccessful()) {
                log.info("OPEN LIBRARY RESPONSE - STATUS CODE: {}, HEADERS: {}", response.statusCode(), response.headers().asHttpHeaders());
            }
            else if (status.is4xxClientError()) {
                throw new ExternalException("Unable to complete request to Open Library. Bad request.");
            }
            else if (status.is5xxServerError()) {
                throw new ExternalException("Failed to get response from Open Library. Try again later.");
            }
        });
    }
}
