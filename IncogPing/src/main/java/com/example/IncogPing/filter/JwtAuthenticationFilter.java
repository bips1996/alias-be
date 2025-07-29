package com.example.IncogPing.filter;

import com.example.IncogPing.entity.User;
import com.example.IncogPing.repository.UserRepository;
import com.example.IncogPing.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    public JwtAuthenticationFilter(JwtUtil jwtUtil, UserRepository userRepository) {
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();
        return path.startsWith("/auth/");
    }

    @Override
    protected void doFilterInternal(jakarta.servlet.http.HttpServletRequest request, jakarta.servlet.http.HttpServletResponse response, jakarta.servlet.FilterChain filterChain) throws jakarta.servlet.ServletException, IOException {
        System.out.println("I am here");
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            handleAuthFailure(response, "Invalid or expired token", 403);
            return;
        }


            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                String token = authorizationHeader.substring(7);

                try {
                    String email = jwtUtil.extractPhoneNumber(token);

                    Optional<User> user = userRepository.findByPhoneNumber(email);

                    if (user.isPresent()) {
                        UserDetails userDetails = org.springframework.security.core.userdetails.User.builder()
                                .username(user.get().getPhoneNumber())
                                .password("") // No password is required for token-based authentication
                                .authorities("ROLE_USER") // Assign a default role
                                .build();

                        UsernamePasswordAuthenticationToken authenticationToken =
                                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                        // Set the Authentication in the SecurityContextHolder
                        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

                        // Optionally add the user entity to the request context
                        request.setAttribute("user", user.get());
                    } else {
                        handleAuthFailure(response,"user Not found", 401);
                    }
                }
                catch(Exception e) {
                    System.out.println("authentication failed");
                     handleAuthFailure(response, "Invalid or expired token", 403);
                     return;
                }
            }
            filterChain.doFilter(request, response);

    }

    private void handleAuthFailure(HttpServletResponse response, String message, int statusCode) throws IOException {
        response.setStatus(statusCode);
        response.setContentType("application/json");
        response.getWriter().write("{\"error\": \"" + message + "\"}");
        response.getWriter().flush();
    }

}
