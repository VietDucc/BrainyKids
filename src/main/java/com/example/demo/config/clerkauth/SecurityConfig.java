package com.example.demo.config.clerkauth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {
    @Autowired
    private ClerkAuthFilter clerkAuthFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/v3/api-docs/**",  // API docs
                                "/swagger-ui/**",   // Swagger UI
                                "/swagger-ui.html"  // Swagger main page
                        ).permitAll()  // Mở quyền truy cập
                        .anyRequest().authenticated()  // Còn lại cần xác thực
                )
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(clerkAuthFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
