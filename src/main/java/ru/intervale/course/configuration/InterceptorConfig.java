package ru.intervale.course.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import ru.intervale.course.utils.interceptors.ControllerInterceptor;

/**
 * Регистрация интерцептора для контроллеров
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    @Autowired
    ControllerInterceptor controllerInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(controllerInterceptor);
    }
}