package com.mail;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
@EnableScheduling
@SpringBootApplication
public class EmailAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmailAppApplication.class, args);
	}

}
