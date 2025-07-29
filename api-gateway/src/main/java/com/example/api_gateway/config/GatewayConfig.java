package com.example.api_gateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("user-service", r -> r.path("/api/users/**")
                        .filters(f -> f.rewritePath("/api/users/(?<segment>.*)", "/users/${segment}"))
                        .uri("lb://USER-SERVICE"))
                .route("group-service", r -> r.path("/api/groups/**")
                        .uri("lb://GROUP-SERVICE"))
                .build();
    }
}
