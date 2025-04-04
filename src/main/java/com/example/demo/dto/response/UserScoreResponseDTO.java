package com.example.demo.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserScoreResponseDTO {
    private Long id;
    private String username;
    private int score;
}