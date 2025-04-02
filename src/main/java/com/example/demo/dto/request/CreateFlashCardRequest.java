package com.example.demo.dto.request;

import lombok.*;

@Getter
@Setter
public class CreateFlashCardRequest {
    private Long deckId;
    private String front;
    private String back;
}
