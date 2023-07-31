package com.springboot.pople;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class PopleApplication
{

	public static void main(String[] args) {
		SpringApplication.run(PopleApplication.class, args);
	}

}
