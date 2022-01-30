package com.tnctech.getfit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;

/**
 * To configure a Spring Boot app as a console app
 * @see: https://medium.com/nerd-for-tech/running-spring-boot-console-application-under-ide-maven-16b59465a7e0
 *
 * Goal Read a text file from the command line.
 */
@SpringBootApplication
public class GetfitApplication {

	public static void main(String[] args) {
		SpringApplication.run(GetfitApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
		return args -> {
			System.out.println("Let's inspect the beans provided by Spring Boot:");
			String[] beanNames = ctx.getBeanDefinitionNames();
			Arrays.sort(beanNames);
			for (String beanName: beanNames) {
				System.out.println(beanName);
			}
		};
	}

}
