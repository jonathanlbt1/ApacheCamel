package com.example.simplecamelspringboot;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication//(exclude = ActiveMQAutoConfiguration.class)
@EntityScan(basePackages = "com.example.simplecamelspringboot.beans")
public class SimpleCamelSpringbootApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(SimpleCamelSpringbootApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

	}
}
