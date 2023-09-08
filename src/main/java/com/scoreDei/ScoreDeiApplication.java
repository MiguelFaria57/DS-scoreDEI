package com.scoreDei;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("com.scoreDei.*")
@EntityScan("com.scoreDei.*")
public class ScoreDeiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ScoreDeiApplication.class, args);
	}

}
