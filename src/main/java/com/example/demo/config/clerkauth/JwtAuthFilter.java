package com.example.demo.config.clerkauth;

import com.example.demo.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.security.Key;
import java.security.PublicKey;
import java.util.Base64;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    @Autowired
    private UserRepository userRepository; // Inject UserRepository để tìm kiếm người dùng
    @Value("${clerk.public-key}") // Load Public Key của Clerk từ application.properties
    private String clerkPublicKey;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = authHeader.substring(7); // Remove "Bearer "
        System.out.println("Received token: " + token);

        try {
            String[] parts = token.split("\\.");
            System.out.println("Token has " + parts.length + " parts");

            // Temporary solution: Just extract user ID from token without validation
            // This assumes the token is trustworthy - in production, proper validation is needed
            String userId = extractUserIdFromToken(token);

            if (userId != null) {
                com.example.demo.entity.User user = userRepository.findByClerkUserId(userId)
                        .orElseThrow(() -> new RuntimeException("User not found"));
                String role = user.getRole();
                //Chuyen role thanh GrantedAuthority
                List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority(role));

                UserDetails userDetails = new User(userId, "", authorities);

                // tao authentication token
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, authorities
                );

                SecurityContextHolder.getContext().setAuthentication(authToken);
                System.out.println("Setting authentication for user: " + userId);
            }
        } catch (Exception e) {
            System.err.println("Token processing error: " + e.getMessage());
        }

        filterChain.doFilter(request, response);
    }

    private String extractUserIdFromToken(String token) {
        try {
            String[] parts = token.split("\\.");
            if (parts.length < 2) return null;

            String payloadJson = new String(Base64.getUrlDecoder().decode(parts[1]));
            System.out.println("Decoded payload: " + payloadJson);

            // Dùng JSON lib để parse
            com.fasterxml.jackson.databind.ObjectMapper mapper = new com.fasterxml.jackson.databind.ObjectMapper();
            Map<String, Object> payload = mapper.readValue(payloadJson, Map.class);

            return (String) payload.get("sub"); // Đây chính là Clerk User ID
        } catch (Exception e) {
            System.err.println("Error extracting user ID: " + e.getMessage());
            return null;
        }
    }


    private String extractUserIdFromClerkJWT(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(getPublicKeyFromClerk())
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject(); // User ID trong token
    }

    private PublicKey getPublicKeyFromClerk() {
        // Chuyển đổi Clerk Public Key từ PEM sang PublicKey Java
        return JwtUtils.parsePublicKey(clerkPublicKey);
    }
}