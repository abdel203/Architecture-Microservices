package fr.dauphine.information;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class InformationApplication {

	public static void main(String[] args) {
		SpringApplication.run(InformationApplication.class, args);
	}

}
