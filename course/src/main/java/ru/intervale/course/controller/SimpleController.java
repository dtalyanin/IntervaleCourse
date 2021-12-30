package ru.intervale.course.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@RestController
public class SimpleController {
    @GetMapping("/hello")
    public String getHello() {
        return "200 Hello!";
    }

    @GetMapping("/withParams")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public String getWithParams(@RequestParam(value = "value") String paramValue) {
        return "202 " + paramValue;
    }

    @GetMapping("/withPathVariable/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public String getWithPathVariable(@PathVariable String id) {
        return "201 " + id;
    }

    @PostMapping(value = "/echo", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getJSON() {
        return "200 json";
    }

    @PostMapping(value = "/echo", produces = MediaType.APPLICATION_XML_VALUE)
    public String getXML() {
        return "200 xml";
    }

    @PutMapping("/put")
    public String put() {
        return HttpStatus.OK.toString();
    }
    @GetMapping(value = "/cookie")
    public String getCookie(HttpServletRequest request, HttpServletResponse response) {
        String result;
        if(request.getCookies() == null) {
            response.addCookie(new Cookie("date", LocalDateTime.now().toString()));
            result = "200 OK";
        }
        else {
            Cookie cookie = request.getCookies()[0];
            result = "200 " + cookie.getValue();
            cookie.setValue(LocalDateTime.now().toString());
            response.addCookie(cookie);
        }
        return result;
    }
}
