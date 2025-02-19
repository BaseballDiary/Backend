package com.backend.baseball;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = "com.backend.baseball.Diary.entity") // Diary 엔티티 패키지 스캔

public class BaseballApplication {

	public static void main(String[] args) {
		SpringApplication.run(BaseballApplication.class, args);
	}

}
