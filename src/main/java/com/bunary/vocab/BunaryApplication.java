package com.bunary.vocab;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class BunaryApplication {

	public static void main(String[] args) {
		SpringApplication.run(BunaryApplication.class, args);
	}

}
