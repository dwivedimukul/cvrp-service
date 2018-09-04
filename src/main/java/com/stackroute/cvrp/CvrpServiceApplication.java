package com.stackroute.cvrp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class CvrpServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CvrpServiceApplication.class, args);
		RestTemplate restTemplate=new RestTemplate();
	}
}
