//package com.example.demo.config.clerkauth;
//
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import java.io.IOException;
//import java.util.Collections;
//
//@Component
//public class ClerkAuthFilter extends OncePerRequestFilter {
//
//    @Autowired
//    private ClerkAuthUtil clerkAuthUtil;
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
//            throws ServletException, IOException {
//        String authHeader = request.getHeader("Authorization");
//
//        if (authHeader != null && authHeader.startsWith("Bearer ")) {
//            String token = authHeader.substring(7);
//            if (clerkAuthUtil.validateToken(token)) {
//                UserDetails userDetails = new User("clerkUser", "", Collections.emptyList());
//                SecurityContextHolder.getContext().setAuthentication(
//                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities())
//                );
//            }
//        }
//
//        filterChain.doFilter(request, response);
//    }
//}
