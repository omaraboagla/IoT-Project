package com.iotproject.iotproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class IotprojectApplication {

	public static void main(String[] args) {
		SpringApplication.run(IotprojectApplication.class, args);
	}
}