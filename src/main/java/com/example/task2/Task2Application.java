package com.example.task2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class Task2Application {

	public static void main(String[] args) {
		SpringApplication.run(Task2Application.class, args);
	}

}
