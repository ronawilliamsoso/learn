package com.example.learn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableAutoConfiguration
public class LearnApplication{

	public static void main(String[] args) {
		SpringApplication.run(LearnApplication.class,args);
	}

	@Bean
	public RestTemplate restTemplate(){return new RestTemplate();}

}
