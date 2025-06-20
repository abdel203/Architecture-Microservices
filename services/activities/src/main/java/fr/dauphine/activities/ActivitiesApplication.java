package fr.dauphine.activities;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@EnableMongoAuditing
@SpringBootApplication
public class ActivitiesApplication {

	public static void main(String[] args) {
		SpringApplication.run(ActivitiesApplication.class, args);
	}

}
