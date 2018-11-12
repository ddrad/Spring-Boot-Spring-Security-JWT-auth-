package com.azaroff.x3.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(
        {"com.azaroff.x3.notification",
        "com.azaroff.x3.confirmation",
        "com.azaroff.x3.connector"})
public class AppConfiguration {
}
