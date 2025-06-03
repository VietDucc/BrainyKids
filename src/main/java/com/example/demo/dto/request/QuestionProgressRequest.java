package com.example.demo.dto.request;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuestionProgressRequest {
    private String userId;
    private boolean completed;
}