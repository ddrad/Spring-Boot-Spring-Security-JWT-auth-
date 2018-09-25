package org.camunda.bpm.getstarted.loanapproval;

import org.camunda.bpm.spring.boot.starter.annotation.EnableProcessApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableProcessApplication
public class WebappProcessApplication {

	public static void main(String... args) {
		SpringApplication.run(WebappProcessApplication.class, args);
	}
	
}