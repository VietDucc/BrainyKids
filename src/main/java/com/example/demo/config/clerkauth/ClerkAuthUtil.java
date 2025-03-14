package com.example.demo.config.clerkauth;

import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class ClerkAuthUtil {
    private static final String CLERK_API_URL = "https://api.clerk.dev/v1/me";
    private static final String CLERK_SECRET_KEY = "sk_test_l07VMBsLaiQOMbNRFGrPSgstg2g20wUTrpwDST4ASM";  // Thay bằng Secret Key từ Clerk

    public boolean validateToken(String token) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(
                CLERK_API_URL,
                HttpMethod.GET,
                entity,
                String.class
        );

        return response.getStatusCode() == HttpStatus.OK;
    }
}