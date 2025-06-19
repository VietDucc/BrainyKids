package com.example.demo.dto.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class TestSubmissionResponse {
    private boolean passed;
    private int correctAnswer;
    private int totalQuestions;
    private double scorePercent;
}
