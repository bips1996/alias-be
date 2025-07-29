package com.example.IncogPing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
@EntityScan(basePackages = "com.example.IncogPing.entity")
public class IncogPingApplication {
	public static void main(String[] args) {
		SpringApplication.run(IncogPingApplication.class, args);
	}
}