package ru.intervale.course.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;

@RestController
public class HttpController {
    @GetMapping("/hello")
    public ResponseEntity<String> getHello() {
        return new ResponseEntity<>("200 Hello", HttpStatus.OK);
    }

    @GetMapping("/withParams")
    public ResponseEntity<String> getWithParams(@RequestParam("singleParamName") String paramValue) {
        return new ResponseEntity<>("202 " + paramValue, HttpStatus.ACCEPTED);
    }

    @GetMapping("/withPathVariable/{id}")
    public ResponseEntity<String> getWithPath(@PathVariable("id") int id) {
        return new ResponseEntity<>("201 for " + id, HttpStatus.CREATED);
    }

    @PostMapping(value = "/echo", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> postJson() {
        return new ResponseEntity<>("200 json", HttpStatus.OK);
    }

    @PostMapping(value = "/echo", consumes = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<String> postXml() {
        return new ResponseEntity<>("200 xml", HttpStatus.OK);
    }

    @PutMapping("put")
    public ResponseEntity<String> put() {
        return new ResponseEntity<>("200 OK", HttpStatus.OK);
    }

    @GetMapping("/cookie")
    public ResponseEntity<String> getCookie(@CookieValue(name = "date", required = false) String date, HttpServletResponse response) {
        ResponseEntity<String> responseWithCookie;
        Cookie cookie = new Cookie("date", LocalDateTime.now().toString());
        cookie.setMaxAge(5);
        if (date == null) {
            responseWithCookie = new ResponseEntity<>("200 OK", HttpStatus.OK);
        }
        else {
            responseWithCookie = new ResponseEntity<>("200 " + date, HttpStatus.OK);
        }
        response.addCookie(cookie);
        return responseWithCookie;
    }
}
