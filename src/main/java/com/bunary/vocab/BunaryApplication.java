package com.bunary.vocab;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.bunary.vocab.service.user.UserService;

;

@EnableScheduling
@SpringBootApplication
@EnableAsync
public class BunaryApplication {

	public static void main(String[] args) {
		SpringApplication.run(BunaryApplication.class, args);
	}

	@Bean
	public CommandLineRunner run(UserService userService) {
		return args -> {

		};
	}

}
