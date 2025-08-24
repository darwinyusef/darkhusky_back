package com.darkhusky.darkhusky_back;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages = "com.darkhusky.darkhusky_back.infrastructure.entity")
@EnableJpaRepositories(basePackages = "com.darkhusky.darkhusky_back.infrastructure.repository")
public class DarkhuskyBackApplication {

	public static void main(String[] args) {
		SpringApplication.run(DarkhuskyBackApplication.class, args);
	}

}