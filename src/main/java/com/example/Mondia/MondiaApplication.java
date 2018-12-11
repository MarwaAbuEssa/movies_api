package com.example.Mondia;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class MondiaApplication {
	public static void main(String[] args) {
		SpringApplication.run(MondiaApplication.class, args);
	}
}
