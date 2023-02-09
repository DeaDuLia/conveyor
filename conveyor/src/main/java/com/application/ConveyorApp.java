package com.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class ConveyorApp {

	public static void main(String[] args) {
		SpringApplication.run(ConveyorApp.class, args);
	}
}
