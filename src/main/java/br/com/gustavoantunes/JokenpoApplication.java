package br.com.gustavoantunes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class JokenpoApplication {

	public static void main(String[] args) {
		SpringApplication.run(JokenpoApplication.class, args);
	}

}
