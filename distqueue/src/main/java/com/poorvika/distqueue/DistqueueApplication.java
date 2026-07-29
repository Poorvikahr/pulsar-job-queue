package com.poorvika.distqueue;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class DistqueueApplication {

	public static void main(String[] args) {
		SpringApplication.run(DistqueueApplication.class, args);
	}

}
