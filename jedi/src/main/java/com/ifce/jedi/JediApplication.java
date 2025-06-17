package com.ifce.jedi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class JediApplication {

	public static void main(String[] args) {
		SpringApplication.run(JediApplication.class, args);
	}

}
