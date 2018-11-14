package com.azaroff.x3.service.business;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.reactive.config.EnableWebFlux;

@SpringBootApplication
@EnableWebFlux
public class BusinessWebApp {

    public static void main(String[] args) {
        SpringApplication.run(BusinessWebApp.class, args);
    }
}