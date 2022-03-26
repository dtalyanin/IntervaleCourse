package ru.intervale.course.utils.interceptors;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Интерцептор по логированию запросов к контроллерам.
 */
@Component
@Slf4j
public class ControllerInterceptor implements HandlerInterceptor {

    /**
     * Осуществляет логирование запроса перед отправкой к контроллеру
     * @param request текущий запрос
     * @param response текущий ответ
     * @param handler выбранный обработчик для выполнения
     * @return true, чтобы цепочка выполнения продолжалась со следующим перехватчиком
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("REQUEST - URL: {}, METHOD: {}", request.getRequestURL(), request.getMethod());
        return true;
    }

    /**
     * Осуществляет логирование ответа перед отправкой к клиенту
     * @param request текущий запрос
     * @param response текущий ответ
     * @param handler обработчик для проверки типа и/или экземпляра
     * @param modelAndView модель, возвращемая обработчиком (также может быть нулевой).
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        log.info("RESPONSE - STATUS CODE: {}", HttpStatus.valueOf(response.getStatus()));
    }

    /**
     * Осуществляет логирование исключения, возникающее при выполнении обработчика
     * @param request текущий запрос
     * @param response текущий ответ
     * @param handler обработчик для проверки типа и/или экземпляра
     * @param ex любое исключение, возникающее при выполнении обработчика
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        if (response.getStatus() >= 400) {
            log.info("RESPONSE ERROR: {}, EXCEPTION: {}", HttpStatus.valueOf(response.getStatus()), ex.getCause().getMessage());
        }
    }

}