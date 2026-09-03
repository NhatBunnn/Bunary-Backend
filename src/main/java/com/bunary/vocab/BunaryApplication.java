package com.bunary.vocab;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.bunary.vocab.service.user.UserService;

@EnableScheduling
@SpringBootApplication
@EnableAsync
public class BunaryApplication {

	public static void main(String[] args) {
		// Nạp file .env từ thư mục gốc của project backend
		Dotenv dotenv = Dotenv.configure()
				.ignoreIfMissing() // Không hoảng loạn nếu không tìm thấy file .env (ví dụ khi deploy Docker)
				.load();

		// Đẩy tất cả biến trong .env vào System Properties để Spring Boot hiểu
		// ${VARIABLE_NAME}
		dotenv.entries().forEach(entry -> {
			System.setProperty(entry.getKey(), entry.getValue());
		});

		SpringApplication.run(BunaryApplication.class, args);
	}

	@Bean
	public CommandLineRunner run(UserService userService) {
		return args -> {

		};
	}
}