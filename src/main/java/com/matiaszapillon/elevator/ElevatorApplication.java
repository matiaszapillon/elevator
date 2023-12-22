package com.matiaszapillon.elevator;

import com.matiaszapillon.elevator.service.ElevatorService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class ElevatorApplication {

	public static void main(String[] args) {
		SpringApplication.run(ElevatorApplication.class, args);
	}


	/*
		@Override
	public void run(String... args) throws Exception {

	}
	 */

}
