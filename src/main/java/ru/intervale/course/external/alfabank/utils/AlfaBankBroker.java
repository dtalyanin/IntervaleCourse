package ru.intervale.course.external.alfabank.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.Instant;


@Slf4j
@Component
public class AlfaBankBroker {
    @Autowired
    private JmsTemplate jmsTemplate;

    public void sengMessage(String headers) {
        String address;
        try {
            address = InetAddress.getLocalHost().toString();
        } catch (UnknownHostException e) {
            address = "0.0.0.0";
        }
        String message = "HEADERS: " + headers + ", UTC: " + Instant.now() + ", IP: " + address;
        jmsTemplate.convertAndSend("audit", message);
    }

    @JmsListener(destination = "audit")
    public void receiveMessage(String message) {
        jmsTemplate.convertAndSend("audit-reply", message);
    }
}