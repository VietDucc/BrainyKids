package com.example.demo.dto.request;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExamProgressRequest {
    private String userId;
    private Long examId;
    private Integer completedQuestions;
    private Integer totalQuestions;
    private Double score;
    private boolean completed;
}