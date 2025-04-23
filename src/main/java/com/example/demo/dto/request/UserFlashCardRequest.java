package com.example.demo.dto.request;

import lombok.Data;

@Data
public class UserFlashCardRequest {
    private String front;
    private String back;
    private Long deckId;
}