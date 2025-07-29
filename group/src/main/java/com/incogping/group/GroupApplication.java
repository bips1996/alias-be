package com.incogping.group;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
@EntityScan(basePackages = "com.incogping.group.entity")
public class GroupApplication {
	public static void main(String[] args) {
		SpringApplication.run(GroupApplication.class, args);
	}

}
