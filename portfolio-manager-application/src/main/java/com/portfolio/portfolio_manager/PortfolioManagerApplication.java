package com.portfolio.portfolio_manager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@SpringBootApplication
@EnableMongoAuditing
public class PortfolioManagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(PortfolioManagerApplication.class, args);
	}
}
