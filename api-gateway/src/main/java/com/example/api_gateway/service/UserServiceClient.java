package com.example.api_gateway.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;

public class UserServiceClient {
    private final RestTemplate restTemplate;
    public UserServiceClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Map<String, Object> getUserInfo(String email) {
        String url = UriComponentsBuilder.fromHttpUrl("http://USER-SERVICE/api/users/email/{email}")
                .buildAndExpand(email)
                .toUriString();

        ResponseEntity<Map> response = restTemplate.getForEntity(url, Map.class);
        return response.getBody();
    }
}
