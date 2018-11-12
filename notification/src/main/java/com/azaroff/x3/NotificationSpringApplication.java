package com.azaroff.x3;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.jms.annotation.EnableJms;

@SpringBootApplication
@EnableJms
public class NotificationSpringApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(NotificationSpringApplication.class, args);
    }
}
