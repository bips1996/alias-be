package com.example.api_gateway.filter;

import com.example.api_gateway.service.UserServiceClient;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Component
public class AuthenticationFilter implements WebFilter {
    private final UserServiceClient userServiceClient;
    private static final String SECRET_KEY = "YourSecretKeyReplaceThisWithAProperOne";
    private static final String AUTH_HEADER = "Authorization";

    public AuthenticationFilter(UserServiceClient userServiceClient) {
        this.userServiceClient = userServiceClient;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();

        // 1️⃣ Check if Authorization header is present
        if (!request.getHeaders().containsKey(AUTH_HEADER)) {
            return unauthorized(exchange);
        }

        String token = request.getHeaders().getFirst(AUTH_HEADER);
        try {
            // 2️⃣ Decode JWT Token
            Claims claims = decodeJWT(token);

            // 3️⃣ Get Email from Token
            String email = claims.get("email", String.class);

            // 4️⃣ Call user-service to get user info
            Map<String, Object> userInfo = userServiceClient.getUserInfo(email);

            // 5️⃣ Add User Details to Request Headers
            ServerHttpRequest modifiedRequest = exchange.getRequest().mutate()
                    .header("X-User-Id", userInfo.get("id").toString())
                    .header("X-User-Email", email)
                    .build();

            return chain.filter(exchange.mutate().request(modifiedRequest).build());
        } catch (Exception e) {
            return unauthorized(exchange);
        }
    }

    private Claims decodeJWT(String token) {
        SecretKey key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token.replace("Bearer ", ""))
                .getBody();
    }

    private Mono<Void> unauthorized(ServerWebExchange exchange) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(org.springframework.http.HttpStatus.UNAUTHORIZED);
        return response.setComplete();
    }
}

