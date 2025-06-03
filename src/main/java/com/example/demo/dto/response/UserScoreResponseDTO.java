package com.example.demo.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserScoreResponseDTO {
    private Long id;
    private String clerkUserId;
    private String username;
    private String profile_image_url;
    private int score;
}