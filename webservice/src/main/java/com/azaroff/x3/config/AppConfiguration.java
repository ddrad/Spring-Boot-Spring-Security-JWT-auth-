package com.azaroff.x3.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"com.azaroff.x3.business", "com.azaroff.x3.user"})
public class AppConfiguration {
}
